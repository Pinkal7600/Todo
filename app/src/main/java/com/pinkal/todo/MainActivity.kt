package com.pinkal.todo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.pinkal.todo.category.fragment.CategoryFragment
import com.pinkal.todo.task.fragment.DashboardFragment
import com.pinkal.todo.task.fragment.HistoryFragment
import com.pinkal.todo.utils.APP_PACKAGE_NAME
import com.pinkal.todo.utils.toastMessage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val TAG: String = MainActivity::class.java.simpleName

    val mActivity: Activity = this@MainActivity

    lateinit var handler: Handler
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()

        checkForUpdate().execute()

        // loading dashboard fragment
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.framLayout, DashboardFragment())
        ft.commit()
    }

    /**
     * initializing views and data
     * */
    fun initialize() {

        setSupportActionBar(toolbarMain)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        handler = Handler()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            toastMessage(mActivity, getString(R.string.please_click_again_to_exit))

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    /**
     * inflating actionbar menu
     * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * actionbar click
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

//        when (id) {
//            R.id.action_settings -> {
//                startActivity(Intent(mActivity, SettingActivity::class.java))
//                return true
//            }
//        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        /**
         * @Handler
         * load fragment after delay of drawer close
         * */
        handler.postDelayed({ navigate(item.itemId) }, 280)

        return true
    }

    /**
     * Navigation Drawer item clicks
     * */
    fun navigate(id: Int) {
        var fragment: Fragment? = null
        var fragmentClass: Class<*>? = null

        when (id) {
            R.id.nav_dashboard -> {
                fragmentClass = DashboardFragment::class.java
                toolbarMain.title = getString(R.string.dashboard)
            }
            R.id.nav_category -> {
                fragmentClass = CategoryFragment::class.java
                toolbarMain.title = getString(R.string.category)
            }
            R.id.nav_history -> {
                fragmentClass = HistoryFragment::class.java
                toolbarMain.title = getString(R.string.history)
            }
            R.id.nav_rate_us -> {
                rateUs()
            }
            R.id.nav_share_app -> {
                shareApp()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)

        try {
            fragment = fragmentClass!!.newInstance() as Fragment

            val fragmentManager = supportFragmentManager

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.framLayout, fragment).commit()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun rateUs() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PACKAGE_NAME)))
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }

    private fun shareApp() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Todo App\n\n" +
                "Try this app, it is Awesome app for daily tasks. And also Share and Rate this app.\n\n" +
                "Download Link : https://play.google.com/store/apps/details?id=com.todo.android")
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Send to..."))
    }

    private inner class checkForUpdate : AsyncTask<String, String, String>() {

        var pInfo = packageManager.getPackageInfo(packageName, 0)
        val oldVersion: String = pInfo.versionName
        lateinit var newVersion: String

        override fun doInBackground(vararg urls: String): String? {


            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" +
                    APP_PACKAGE_NAME + "&hl=en")
                    .timeout(30000)
                    .userAgent(
                            "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get()
                    .select("div[itemprop=softwareVersion]").first()
                    .ownText()

            Log.e("new Version", newVersion)
            Log.e("old Version", oldVersion)

            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (newVersion.equals(oldVersion)) {
                Log.e(TAG, "Update not available version : " + newVersion)
            } else {
                Log.e(TAG, "Update available version : " + newVersion)
            }
        }

    }
}
