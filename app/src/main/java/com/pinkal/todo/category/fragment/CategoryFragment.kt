package com.pinkal.todo.category.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pinkal.todo.R
import com.pinkal.todo.category.`interface`.CategoryAdd
import com.pinkal.todo.category.`interface`.CategoryIsEmpty
import com.pinkal.todo.category.adapter.CategoryAdapter
import com.pinkal.todo.category.database.DBManagerCategory
import com.pinkal.todo.category.model.CategoryModel
import com.pinkal.todo.utils.dialogAddCategory
import kotlinx.android.synthetic.main.fragment_category.view.*
import java.util.*

/**
 * Created by Pinkal on 22/5/17.
 */
class CategoryFragment : Fragment(), View.OnClickListener, CategoryAdd, CategoryIsEmpty {

    val TAG: String = CategoryFragment::class.java.simpleName

    lateinit var fabAddCategory: FloatingActionButton
    lateinit var recyclerViewCategory: RecyclerView
    lateinit var txtNoCategory: TextView

    var mArrayList: ArrayList<CategoryModel> = ArrayList()
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_category, container, false)

        initialize(view)

        return view
    }

    /**
     * initializing views and data
     * */
    private fun initialize(view: View) {

        fabAddCategory = view.fabAddCategory
        recyclerViewCategory = view.recyclerViewCategory
        txtNoCategory = view.txtNoCategory

        recyclerViewCategory.setHasFixedSize(true)
        recyclerViewCategory.layoutManager = LinearLayoutManager(activity!!) as RecyclerView.LayoutManager

        fabAddCategory.setOnClickListener(this)

        val dbManageCategory = DBManagerCategory(activity)
        mArrayList = dbManageCategory.getCategoryList()

        categoryAdapter = CategoryAdapter(activity, mArrayList, this)
        recyclerViewCategory.adapter = categoryAdapter
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
                dialogAddCategory(activity, this)
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

            categoryAdapter.clearAdapter()
            categoryAdapter.setList(mArrayList)
        }
    }

    override fun categoryIsEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            txtNoCategory.visibility = View.VISIBLE
        } else {
            txtNoCategory.visibility = View.GONE
        }
    }
}
