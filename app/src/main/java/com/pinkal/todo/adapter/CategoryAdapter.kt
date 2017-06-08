package com.pinkal.todo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pinkal.todo.R
import com.pinkal.todo.`interface`.CategoryDelete
import com.pinkal.todo.`interface`.CategoryIsEmpty
import com.pinkal.todo.`interface`.CategoryUpdate
import com.pinkal.todo.model.CategoryModel
import com.pinkal.todo.utils.dialogDeleteCategory
import com.pinkal.todo.utils.dialogUpdateCategory

/**
 * Created by Pinkal on 25/5/17.
 */
class CategoryAdapter(val mContext: Context, mArrayList: ArrayList<CategoryModel>, categoryIsEmpty: CategoryIsEmpty) :
        RecyclerView.Adapter<CategoryAdapter.ViewHolder>(), CategoryUpdate, CategoryDelete {

    val TAG = CategoryAdapter::class.java.simpleName!!

    var mArrayList: ArrayList<CategoryModel> = ArrayList()

    var inflater: LayoutInflater
    var isEmpty: CategoryIsEmpty = categoryIsEmpty

    init {
        this.mArrayList = mArrayList
        inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    /**
     * Binding views with Adapter
     * */
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.txtCategoryName.text = mArrayList[position].categoryName
        holder.imgEditCategory.setOnClickListener({
            dialogUpdateCategory(mContext, mArrayList[position].id!!, this)
        })
        holder.imgDeleteCategory.setOnClickListener({
            dialogDeleteCategory(mContext, mArrayList[position].id!!, this)
        })
    }

    override fun getItemCount(): Int {
        Log.e(TAG, "size : " + mArrayList.size)
        if (mArrayList.size == 0) {
            isEmpty.categoryIsEmpty(true)
        } else {
            isEmpty.categoryIsEmpty(false)
        }
        return mArrayList.size
    }

    /**
     * Clear list data
     * */
    fun clearAdapter() {
        this.mArrayList.clear()
        notifyDataSetChanged()
    }

    /**
     * Set new data list
     * */
    fun setList(mArrayList: ArrayList<CategoryModel>) {
        this.mArrayList = mArrayList
        notifyDataSetChanged()
    }

    /**
     * Inflating layout
     * */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.row_category, parent, false)
        return ViewHolder(mView)
    }

    /**
     * update list when category is updated
     * */
    override fun isCategoryUpdated(isUpdated: Boolean, mArrayList: ArrayList<CategoryModel>) {
        clearAdapter()
        setList(mArrayList)
    }

    /**
     * update list when category is deleted
     * */
    override fun isCategoryDeleted(isDeleted: Boolean, mArrayList: ArrayList<CategoryModel>) {
        if (isDeleted) {
            clearAdapter()
            setList(mArrayList)
        }
    }

    /**
     * @ViewHolder class
     * initialize view
     * */
    class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {
        val txtCategoryName: TextView = view!!.findViewById(R.id.txtCategoryName) as TextView
        val imgEditCategory: ImageView = view!!.findViewById(R.id.imgEditCategory) as ImageView
        val imgDeleteCategory: ImageView = view!!.findViewById(R.id.imgDeleteCategory) as ImageView
    }
}