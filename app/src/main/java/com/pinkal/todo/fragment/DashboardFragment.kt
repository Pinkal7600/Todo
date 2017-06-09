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
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.util.*


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

    private val paint = Paint()


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
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        Log.e(TAG, "item long click Position : " + position)
                    }
                })
        )
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

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    if (dX > 0) {
                        paint.color = Color.parseColor(getString(R.color.green))
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left.toFloat() + dX, itemView.bottom.toFloat())
                        c.drawRect(background, paint)
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_check_white_png)
                        val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, paint)
                    } else {
                        paint.color = Color.parseColor(getString(R.color.red))
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, paint)
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_delete_white_png)
                        val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, paint)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewTask)
    }

    fun isTaskListEmpty() {
        if (taskAdapter.itemCount == 0) {
            txtNoTask.visibility = View.VISIBLE
        } else {
            txtNoTask.visibility = View.GONE
        }
    }

}
