package com.pinkal.todo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pinkal.todo.utils.Constant


/**
 * Created by Pinkal on 25/5/17.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION) {


    /**************** Category ****************/

    private val CREATE_CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Constant.TABLE_CATEGORY + "(" +
                    Constant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constant.CATEGORY_NAME + " TEXT); "

    private val DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + Constant.TABLE_CATEGORY


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_CATEGORY_TABLE)

        val cv = ContentValues()
        cv.put(Constant.CATEGORY_NAME, "Personal")
        db.insert(Constant.TABLE_CATEGORY, null, cv)

        val cv1 = ContentValues()
        cv1.put(Constant.CATEGORY_NAME, "Business")
        db.insert(Constant.TABLE_CATEGORY, null, cv1)

        val cv2 = ContentValues()
        cv2.put(Constant.CATEGORY_NAME, "Insurance")
        db.insert(Constant.TABLE_CATEGORY, null, cv2)

        val cv3 = ContentValues()
        cv3.put(Constant.CATEGORY_NAME, "Shopping")
        db.insert(Constant.TABLE_CATEGORY, null, cv3)

        val cv4 = ContentValues()
        cv4.put(Constant.CATEGORY_NAME, "Banking")
        db.insert(Constant.TABLE_CATEGORY, null, cv4)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DROP_CATEGORY_TABLE)
        onCreate(db)
    }
}