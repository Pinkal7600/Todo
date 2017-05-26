package com.pinkal.todo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkal.todo.R

/**
 * Created by Pinkal on 22/5/17.
 */
class HistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_history, container, false)

        return view
    }
}