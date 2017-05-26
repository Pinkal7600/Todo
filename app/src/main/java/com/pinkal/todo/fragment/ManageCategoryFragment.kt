package com.pinkal.todo.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkal.todo.R
import com.pinkal.todo.`interface`.CategoryAdd
import com.pinkal.todo.adapter.CategoryAdapter
import com.pinkal.todo.database.manager.DBManagerCategory
import com.pinkal.todo.model.CategoryModel
import com.pinkal.todo.utils.CommonUtils
import java.util.*

/**
 * Created by Pinkal on 22/5/17.
 */
class ManageCategoryFragment : Fragment(), View.OnClickListener, CategoryAdd {

    val TAG: String = ManageCategoryFragment::class.java.simpleName

    var fab: FloatingActionButton? = null

    var recyclerView: RecyclerView? = null
    var mArrayList: ArrayList<CategoryModel> = ArrayList()
    var categoryAdapter: CategoryAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_manage_category, container, false)

        initialize(view)

        return view
    }

    /**
     * initializing views and data
     * */
    private fun initialize(view: View) {
        fab = view.findViewById(R.id.fabAddCategory) as FloatingActionButton
        recyclerView = view.findViewById(R.id.rvCategory) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(activity!!) as RecyclerView.LayoutManager

        fab!!.setOnClickListener(this)

        val dbManageCategory = DBManagerCategory(activity)
        mArrayList = dbManageCategory.getCategoryList()

        categoryAdapter = CategoryAdapter(activity, mArrayList)
        recyclerView!!.adapter = categoryAdapter
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "Resume")
    }

    /**
     * Views clicks
     * */
    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.fabAddCategory -> {
                CommonUtils.dialogAddCategory(activity, this)
            }
        }
    }

    /**
     * If new category is added
     * then RecycleView will update
     *
     * @Boolean is category added or not
     * */
    override fun isCategoryAdded(isAdded: Boolean) {
        if (isAdded) {

            Log.e(TAG, "true : " + isAdded)

            val dbManageCategory = DBManagerCategory(activity)
            mArrayList = dbManageCategory.getCategoryList()

            categoryAdapter!!.clearAdapter()
            categoryAdapter!!.setList(mArrayList)
        }
    }
}
