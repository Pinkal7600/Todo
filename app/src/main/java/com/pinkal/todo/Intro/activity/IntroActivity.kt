package com.pinkal.todo.Intro.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.pinkal.todo.Intro.adapter.IntroAdapter
import com.pinkal.todo.MainActivity
import com.pinkal.todo.R
import com.pinkal.todo.utils.TOTAL_INTRO_PAGES
import kotlinx.android.synthetic.main.activity_intro.*


/**
 * Created by Pinkal on 13/6/17.
 */
class IntroActivity : AppCompatActivity() {

    val mActivity: Activity = this@IntroActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        initialize()
    }

    private fun initialize() {

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.redPrimaryDark)

        viewPagerIntro.isFocusable = true
        viewPagerIntro.adapter = IntroAdapter(supportFragmentManager)

        circleIndicator.radius = 12f
        circleIndicator.setViewPager(viewPagerIntro)

        val density = resources.displayMetrics.density

        circleIndicator.setBackgroundColor(Color.TRANSPARENT)
        circleIndicator.strokeWidth = 0f
        circleIndicator.radius = 5 * density
        circleIndicator.pageColor = resources.getColor(R.color.redPrimaryDark) // background color
        circleIndicator.fillColor = resources.getColor(R.color.colorWhite) // dots fill color

        txtSkipIntro.setOnClickListener({
            startActivity(Intent(mActivity, MainActivity::class.java))
            finish()
        })

        circleIndicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position < TOTAL_INTRO_PAGES - 1) {
                    txtSkipIntro.visibility = View.VISIBLE
                } else if (position == TOTAL_INTRO_PAGES - 1) {
                    txtSkipIntro.visibility = View.GONE
                }

                when (position) {
                    0 -> {
                        circleIndicator.pageColor = resources.getColor(R.color.redPrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.redPrimaryDark)
                    }

                    1 -> {
                        circleIndicator.pageColor = resources.getColor(R.color.purplePrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.purplePrimaryDark)
                    }

                    2 -> {
                        circleIndicator.pageColor = resources.getColor(R.color.tealPrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.tealPrimaryDark)
                    }

                    3 -> {
                        circleIndicator.pageColor = resources.getColor(R.color.indigoPrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.indigoPrimaryDark)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}