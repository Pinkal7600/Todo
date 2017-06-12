package com.pinkal.todo.task.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkal.todo.R
import com.pinkal.todo.task.database.DBManagerTask
import com.pinkal.todo.task.model.TaskModel
import com.pinkal.todo.utils.views.recyclerview.itemdrag.ItemTouchHelperAdapter
import kotlinx.android.synthetic.main.row_task.view.*
import java.util.*

/**
 * Created by Pinkal on 31/5/17.
 */
class TaskAdapter(val mContext: Context, var mArrayList: ArrayList<TaskModel>) :
        RecyclerView.Adapter<TaskAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    val TAG: String = TaskAdapter::class.java.simpleName
    val dbManager: DBManagerTask = DBManagerTask(mContext)

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.row_task, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

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

    fun getList(): ArrayList<TaskModel> {
        return this.mArrayList
    }

    fun deleteTask(position: Int) {
        dbManager.delete(mArrayList[position].id!!)
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    fun finishTask(position: Int) {
        dbManager.finishTask(mArrayList[position].id!!)
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    fun unFinishTask(position: Int) {
        dbManager.unFinishTask(mArrayList[position].id!!)
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    override fun onItemDismiss(position: Int) {
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(mArrayList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewColorTag = view.viewColorTag!!
        val txtShowTitle = view.txtShowTitle!!
        val txtShowTask = view.txtShowTask!!
        val txtShowCategory = view.txtShowCategory!!

        val txtShowDate = view.txtShowDate!!
        val textDate = view.textDate!!
        val txtShowTime = view.txtShowTime!!
        val textTime = view.textTime!!
        val textTitle = view.textTitle!!
        val textTask = view.textTask!!
    }
}