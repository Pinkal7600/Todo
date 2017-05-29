package com.pinkal.todo.database.manager

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.pinkal.todo.database.DatabaseHelper
import com.pinkal.todo.model.CategoryModel
import com.pinkal.todo.utils.CATEGORY_NAME
import com.pinkal.todo.utils.ID
import com.pinkal.todo.utils.TABLE_CATEGORY

/**
 * Created by Pinkal on 25/5/17.
 */
class DBManagerCategory(val context: Context) {

    var dbHelper: DatabaseHelper? = null
    var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): DBManagerCategory {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    /**
     * insert value in Category table
     */
    fun insert(contentValues: ContentValues) {
        open()
        database!!.insert(TABLE_CATEGORY, null, contentValues)
        close()
    }

    /**
     * update value in Category table
     */
    fun update(id: Int, categoryName: String) {
        open()
        val contentValue = ContentValues()

        contentValue.put(CATEGORY_NAME, categoryName)

        database!!.update(TABLE_CATEGORY, contentValue, ID + " = " + id, null)
        close()
    }

    /**
     * delete row in Category table
     */
    fun delete(id: Int) {
        open()
        database!!.delete(TABLE_CATEGORY, ID + "=" + id, null)
        close()
    }

    /**
     * get name from Category table
     */
    fun getCategoryName(id: Int): String {

        var categoryName: String = ""
        open()

        val query = "SELECT * FROM " + TABLE_CATEGORY +
                " WHERE " + ID + "=" + id

        val cursor = database!!.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                categoryName = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME))
            } while (cursor.moveToNext())
        }

        close()
        return categoryName
    }

    /**
     * get category list from Category table
     */
    fun getCategoryList(): ArrayList<CategoryModel> {
        val arrayList = ArrayList<CategoryModel>()

        open()

        val query = "SELECT * FROM " + TABLE_CATEGORY
        val cursor = database!!.rawQuery(query, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val categoryModel = CategoryModel()

                categoryModel.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                categoryModel.categoryName = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME))

                arrayList.add(categoryModel)

            } while (cursor.moveToNext())
        }
        close()
        return arrayList
    }
}