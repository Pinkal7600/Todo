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
import com.pinkal.todo.RecyclerItemClickListener
import com.pinkal.todo.activity.AddTaskActivity
import com.pinkal.todo.adapter.TaskAdapter
import com.pinkal.todo.database.manager.DBManagerTask
import com.pinkal.todo.model.TaskModel
import com.pinkal.todo.utils.DASHBOARD_RECYCLEVIEW_REFRESH
import java.util.*


/**
 * Created by Pinkal on 22/5/17.
 */
class DashboardFragment : Fragment(), View.OnClickListener {

    val TAG: String = DashboardFragment::class.java.simpleName

    var fab: FloatingActionButton? = null
    var txtNoTask: TextView? = null
    var recyclerView: RecyclerView? = null

    var mArrayList: ArrayList<TaskModel> = ArrayList()
    var dbManager: DBManagerTask? = null
    var taskAdapter: TaskAdapter? = null

    private val paint = Paint()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        initialize(view)

        return view
    }

    private fun initialize(view: View) {
        fab = view.findViewById(R.id.fabAddTask) as FloatingActionButton
        txtNoTask = view.findViewById(R.id.txtNoTask) as TextView
        recyclerView = view.findViewById(R.id.rvTask) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(activity!!) as RecyclerView.LayoutManager

        fab!!.setOnClickListener(this)

        dbManager = DBManagerTask(activity)
        mArrayList = dbManager!!.getTaskList()

        taskAdapter = TaskAdapter(activity, mArrayList)
        recyclerView!!.adapter = taskAdapter

        initSwipe()

        recyclerView!!.addOnItemTouchListener(
                RecyclerItemClickListener(context, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // do whatever
                        Log.e(TAG, "item click")
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        Log.e(TAG, "item long click")
                    }
                })
        )
    }

    override fun onResume() {
        super.onResume()
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
                    mArrayList = dbManager!!.getTaskList()
                    taskAdapter!!.clearAdapter()
                    taskAdapter!!.setList(mArrayList)
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
                    taskAdapter!!.deleteTask(position)
                } else {
                    taskAdapter!!.finishTask(position)
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    if (dX > 0) {
                        paint.color = Color.parseColor("#388E3C")
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left.toFloat() + dX, itemView.bottom.toFloat())
                        c.drawRect(background, paint)
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_check_white_png)
                        val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, paint)
                    } else {
                        paint.color = Color.parseColor("#D32F2F")
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
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
