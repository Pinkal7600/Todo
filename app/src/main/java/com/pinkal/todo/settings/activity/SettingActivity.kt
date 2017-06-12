package com.pinkal.todo.settings.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pinkal.todo.R
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by Pinkal on 22/5/17.
 */
class SettingActivity : AppCompatActivity() {

    val TAG: String = SettingActivity::class.java.simpleName

    val mActivity: Activity = this@SettingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initialize()
    }

    /**
     * initializing views and data
     * */
    private fun initialize() {

        setSupportActionBar(toolbarSetting)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    /**
     * action bar back button click
     * */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}