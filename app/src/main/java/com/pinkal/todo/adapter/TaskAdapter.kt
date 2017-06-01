package com.pinkal.todo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pinkal.todo.R
import com.pinkal.todo.database.manager.DBManagerTask
import com.pinkal.todo.model.TaskModel
import java.util.*

/**
 * Created by Pinkal on 31/5/17.
 */
class TaskAdapter(val mContext: Context, var mArrayList: ArrayList<TaskModel>) :
        RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    val TAG: String = TaskAdapter::class.java.simpleName
    val dbManager: DBManagerTask = DBManagerTask(mContext)

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskAdapter.ViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.row_task, parent, false)
        return TaskAdapter.ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder?, position: Int) {

        val androidColors = mContext.resources.getIntArray(R.array.random_color)
        val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
        holder!!.viewColorTag.setBackgroundColor(randomAndroidColor)

        Log.e(TAG, "title : " + mArrayList[position].title)
        Log.e(TAG, "task : " + mArrayList[position].task)
        Log.e(TAG, "category : " + mArrayList[position].category)
        holder.txtShowTitle.text = mArrayList[position].title
        holder.txtShowTask.text = mArrayList[position].task
        holder.txtShowCategory.text = mArrayList[position].category

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
    fun setList(mArrayList: ArrayList<TaskModel>) {
        this.mArrayList = mArrayList
        notifyDataSetChanged()
    }

    fun deleteTask(position: Int) {
        dbManager.delete(mArrayList[position].id!!)
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    fun finishTask(position: Int) {
//        mArrayList.removeAt(position)
//        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewColorTag = view.findViewById(R.id.viewColorTag) as View
        val txtShowTitle = view.findViewById(R.id.txtShowTitle) as TextView
        val txtShowTask = view.findViewById(R.id.txtShowTask) as TextView
        val txtShowCategory = view.findViewById(R.id.txtShowCategory) as TextView

    }
}