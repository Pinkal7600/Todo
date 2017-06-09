package com.pinkal.todo.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pinkal.todo.R
import com.pinkal.todo.activity.AddTaskActivity
import com.pinkal.todo.adapter.TaskAdapter
import com.pinkal.todo.database.manager.DBManagerTask
import com.pinkal.todo.listener.RecyclerItemClickListener
import com.pinkal.todo.model.TaskModel
import com.pinkal.todo.utils.DASHBOARD_RECYCLEVIEW_REFRESH
import com.pinkal.todo.utils.getFormatDate
import com.pinkal.todo.utils.getFormatTime
import kotlinx.android.synthetic.main.fragment_dashboard.view.*


/**
 * Created by Pinkal on 22/5/17.
 */
class DashboardFragment : Fragment(), View.OnClickListener {

    val TAG: String = DashboardFragment::class.java.simpleName

    lateinit var fabAddTask: FloatingActionButton
    lateinit var txtNoTask: TextView
    lateinit var recyclerViewTask: RecyclerView

    var mArrayList: ArrayList<TaskModel> = ArrayList()
    lateinit var dbManager: DBManagerTask
    lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        initialize(view)

        return view
    }

    private fun initialize(view: View) {

        fabAddTask = view.fabAddTask
        txtNoTask = view.txtNoTask
        recyclerViewTask = view.recyclerViewTask

        recyclerViewTask.setHasFixedSize(true)
        recyclerViewTask.layoutManager = LinearLayoutManager(activity!!) as RecyclerView.LayoutManager

        fabAddTask.setOnClickListener(this)

        dbManager = DBManagerTask(activity)
        mArrayList = dbManager.getTaskList()

        taskAdapter = TaskAdapter(activity, mArrayList)
        recyclerViewTask.adapter = taskAdapter

        initSwipe()

        recyclerViewTask.addOnItemTouchListener(
                RecyclerItemClickListener(context, recyclerViewTask, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        Log.e(TAG, "item click Position : " + position)

                        val holder: TaskAdapter.ViewHolder = TaskAdapter.ViewHolder(view)

                        clickForDetails(holder, position)
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        Log.e(TAG, "item long click Position : " + position)
                    }
                })
        )
    }

    private fun clickForDetails(holder: TaskAdapter.ViewHolder, position: Int) {

        val taskList = taskAdapter.getList()

        if (holder.textTitle.visibility == View.GONE && holder.textTask.visibility == View.GONE) {

            holder.textTitle.visibility = View.VISIBLE
            holder.textTask.visibility = View.VISIBLE
            holder.txtShowTitle.maxLines = Integer.MAX_VALUE
            holder.txtShowTask.maxLines = Integer.MAX_VALUE

            if (taskList[position].date != "") {
                holder.txtShowDate.text = getFormatDate(taskList[position].date!!)
                holder.textDate.visibility = View.VISIBLE
                holder.txtShowDate.visibility = View.VISIBLE
            }

            if (taskList[position].time != "") {
                holder.txtShowTime.text = getFormatTime(taskList[position].time!!)
                holder.textTime.visibility = View.VISIBLE
                holder.txtShowTime.visibility = View.VISIBLE
            }

        } else {

            holder.textTitle.visibility = View.GONE
            holder.textTask.visibility = View.GONE
            holder.txtShowTask.maxLines = 1
            holder.txtShowTitle.maxLines = 1

            if (taskList[position].date != "") {
                holder.textDate.visibility = View.GONE
                holder.txtShowDate.visibility = View.GONE
            }

            if (taskList[position].time != "") {
                holder.textTime.visibility = View.GONE
                holder.txtShowTime.visibility = View.GONE
            }

        }
    }

    override fun onResume() {
        super.onResume()
        isTaskListEmpty()
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.fabAddTask -> {
                startActivityForResult(Intent(activity, AddTaskActivity::class.java), DASHBOARD_RECYCLEVIEW_REFRESH)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                DASHBOARD_RECYCLEVIEW_REFRESH -> {
                    mArrayList = dbManager.getTaskList()
                    taskAdapter.clearAdapter()
                    taskAdapter.setList(mArrayList)
                }
            }
        }
    }

    private fun initSwipe() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    taskAdapter.deleteTask(position)
                    isTaskListEmpty()
                } else {
                    taskAdapter.finishTask(position)
                    isTaskListEmpty()
                }
            }

            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                if (actionState === ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    val itemView = viewHolder.itemView

                    val p = Paint()
                    val icon: Bitmap

                    if (dX > 0) {
                        /* Note, ApplicationManager is a helper class I created
                            myself to get a context outside an Activity class -
                            feel free to use your own method */

                        icon = BitmapFactory.decodeResource(
                                resources, R.mipmap.ic_check_white_png)

                        /* Set your color for positive displacement */
                        p.color = Color.parseColor(getString(R.color.green))

                        // Draw Rect with varying right side, equal to displacement dX
                        canvas.drawRect(itemView.left.toFloat(), itemView.top.toFloat(),
                                itemView.left.toFloat() + dX, itemView.bottom.toFloat(), p)

                        // Set the image icon for Right swipe
                        canvas.drawBitmap(icon,
                                itemView.left.toFloat() + convertDpToPx(16),
                                itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                                p)
                    } else {
                        icon = BitmapFactory.decodeResource(
                                resources, R.mipmap.ic_delete_white_png)

                        /* Set your color for negative displacement */
                        p.color = Color.parseColor(getString(R.color.red))


                        // Draw Rect with varying left side, equal to the item's right side
                        // plus negative displacement dX
                        canvas.drawRect(itemView.right.toFloat() + dX, itemView.top.toFloat(),
                                itemView.right.toFloat(), itemView.bottom.toFloat(), p)

                        //Set the image icon for Left swipe
                        canvas.drawBitmap(icon,
                                itemView.right.toFloat() - convertDpToPx(16) - icon.width,
                                itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                                p)
                    }

                    val ALPHA_FULL: Float = 1.0f

                    // Fade out the view as it is swiped out of the parent's bounds
                    val alpha: Float = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width.toFloat()
                    viewHolder.itemView.alpha = alpha
                    viewHolder.itemView.translationX = dX

                } else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewTask)
    }

    private fun convertDpToPx(dp: Int): Int {
        return Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun isTaskListEmpty() {
        if (taskAdapter.itemCount == 0) {
            txtNoTask.visibility = View.VISIBLE
        } else {
            txtNoTask.visibility = View.GONE
        }
    }

}
