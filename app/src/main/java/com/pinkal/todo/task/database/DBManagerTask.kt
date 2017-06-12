package com.pinkal.todo.task.database

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.pinkal.todo.databasehelper.DatabaseHelper
import com.pinkal.todo.task.model.TaskModel
import com.pinkal.todo.utils.*

/**
 * Created by Pinkal on 30/5/17.
 */
class DBManagerTask(val context: Context) {

    lateinit var dbHelper: DatabaseHelper
    lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open(): DBManagerTask {
        dbHelper = DatabaseHelper(context)
        database = dbHelper.writableDatabase
        return this
    }

    fun close() {
        dbHelper.close()
    }

    /**
     * insert value in task table
     */
    fun insert(title: String, task: String, category: String, date: String = "", time: String = "") {
        open()

        val contentValues = ContentValues()
        contentValues.put(TASK_TITLE, title)
        contentValues.put(TASK_TASK, task)
        contentValues.put(TASK_CATEGORY, category)
        contentValues.put(TASK_DATE, date)
        contentValues.put(TASK_TIME, time)
        contentValues.put(TASK_FINISH, TASK_IS_NOT_FINISH)


        database.insert(TABLE_TASK, null, contentValues)
        close()
    }

    /**
     * update value in task table
     */
    fun update(id: Int, title: String, task: String, category: String, date: String = "", time: String = "") {
        open()

        val contentValues = ContentValues()

        contentValues.put(TASK_TITLE, title)
        contentValues.put(TASK_TASK, task)
        contentValues.put(TASK_CATEGORY, category)
        contentValues.put(TASK_DATE, date)
        contentValues.put(TASK_TIME, time)

        database.update(TABLE_TASK, contentValues, ID + " = " + id, null)
        close()
    }

    /**
     * delete row in task table
     */
    fun delete(id: Int) {
        open()
        database.delete(TABLE_TASK, ID + "=" + id, null)
        close()
    }

    /**
     * get task list from Task table
     */
    fun getTaskList(): ArrayList<TaskModel> {

        open()

        val arrayList = ArrayList<TaskModel>()

        val query = "SELECT * FROM " + TABLE_TASK
        val cursor = database.rawQuery(query, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {

                val isFinish = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_FINISH)))

                if (isFinish == TASK_IS_NOT_FINISH) {

                    val taskModel = TaskModel()

                    taskModel.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    taskModel.title = cursor.getString(cursor.getColumnIndex(TASK_TITLE))
                    taskModel.task = cursor.getString(cursor.getColumnIndex(TASK_TASK))
                    taskModel.category = cursor.getString(cursor.getColumnIndex(TASK_CATEGORY))
                    taskModel.date = cursor.getString(cursor.getColumnIndex(TASK_DATE))
                    taskModel.time = cursor.getString(cursor.getColumnIndex(TASK_TIME))

                    arrayList.add(taskModel)

                }

            } while (cursor.moveToNext())
        }
        cursor.close()
        close()
        return arrayList
    }

    /**
     * Finish task
     * */
    fun finishTask(id: Int) {
        open()
        val contentValues = ContentValues()

        contentValues.put(TASK_FINISH, TASK_IS_FINISH)

        database.update(TABLE_TASK, contentValues, ID + " = " + id, null)
        close()
    }

    fun getHistoryTaskList(): java.util.ArrayList<TaskModel> {
        open()

        val arrayList = ArrayList<TaskModel>()

        val query = "SELECT * FROM " + TABLE_TASK
        val cursor = database.rawQuery(query, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {

                val isFinish = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_FINISH)))

                if (isFinish == TASK_IS_FINISH) {

                    val taskModel = TaskModel()

                    taskModel.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    taskModel.title = cursor.getString(cursor.getColumnIndex(TASK_TITLE))
                    taskModel.task = cursor.getString(cursor.getColumnIndex(TASK_TASK))
                    taskModel.category = cursor.getString(cursor.getColumnIndex(TASK_CATEGORY))
                    taskModel.date = cursor.getString(cursor.getColumnIndex(TASK_DATE))
                    taskModel.time = cursor.getString(cursor.getColumnIndex(TASK_TIME))

                    arrayList.add(taskModel)

                }

            } while (cursor.moveToNext())
        }
        cursor.close()
        close()
        return arrayList
    }

    fun unFinishTask(id: Int) {
        open()
        val contentValues = ContentValues()

        contentValues.put(TASK_FINISH, TASK_IS_NOT_FINISH)

        database.update(TABLE_TASK, contentValues, ID + " = " + id, null)
        close()
    }


}