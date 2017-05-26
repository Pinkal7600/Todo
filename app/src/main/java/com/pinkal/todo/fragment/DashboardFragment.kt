package com.pinkal.todo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkal.todo.R
import com.pinkal.todo.activity.AddTaskActivity

/**
 * Created by Pinkal on 22/5/17.
 */
class DashboardFragment : Fragment(), View.OnClickListener {


    var fab: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        var view: View = super.onCreateView(inflater, container, savedInstanceState)!!
        var view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        initialize(view)

        return view
    }

    private fun initialize(view: View) {
        fab = view.findViewById(R.id.fabAddTask) as FloatingActionButton
        fab!!.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.fabAddTask -> {
                startActivity(Intent(activity, AddTaskActivity::class.java))
            }
        }
    }
}