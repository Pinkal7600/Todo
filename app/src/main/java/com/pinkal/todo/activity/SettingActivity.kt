package com.pinkal.todo.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.pinkal.todo.R

/**
 * Created by Pinkal on 22/5/17.
 */
class SettingActivity : AppCompatActivity() {

    val TAG: String = SettingActivity::class.java.simpleName

    val mActivity: Activity = this@SettingActivity

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initialize()
    }

    /**
     * initializing views and data
     * */
    private fun initialize() {
        toolbar = findViewById(R.id.toolbarSetting) as Toolbar

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    /**
     * back arrow button in actionbar click
     * */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}