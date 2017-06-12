package com.pinkal.todo.utils

import android.content.ContentValues
import android.content.Context
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.pinkal.todo.R
import com.pinkal.todo.category.`interface`.CategoryAdd
import com.pinkal.todo.category.`interface`.CategoryDelete
import com.pinkal.todo.category.`interface`.CategoryUpdate
import com.pinkal.todo.category.database.DBManagerCategory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Pinkal on 29/5/17.
 *
 *  @CommonUtils
 * All common methods are declare here.
 * which are repeatedly use in project.
 *
 */


/**
 * Toast message
 * */
fun toastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 * Convert formatted Date
 * */
fun getFormatDate(inputDate: String): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("EEE, d MMM yyyy")

    var date: Date? = null
    try {
        date = inputFormat.parse(inputDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val outputDate = outputFormat.format(date)
    return outputDate
}

/**
 * Convert formatted Time
 * */
fun getFormatTime(inputTime: String): String {

    val inputFormat = SimpleDateFormat("HH:mm") // HH:mm:ss
    val outputFormat = SimpleDateFormat("h:mm a")

    var date: Date? = null
    try {
        date = inputFormat.parse(inputTime)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val outputTime = outputFormat.format(date)
    return outputTime
}

/**
 * dialog to add category
 * */
fun dialogAddCategory(context: Context, categoryAdd: CategoryAdd) {

    val li = LayoutInflater.from(context)
    val promptsView = li.inflate(R.layout.alert_dialog_add_category, null)

    val alert = AlertDialog.Builder(context)
    alert.setView(promptsView)

    val input: EditText = promptsView.findViewById(R.id.edtAddCat) as EditText

    alert.setPositiveButton(R.string.add, { _, _ -> })

    alert.setNegativeButton(R.string.cancel, { _, _ -> })
    val alertDialog = alert.create()

    val recycleUpdate = categoryAdd

    alertDialog.setOnShowListener({

        val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.setOnClickListener({

            val categoryName: String = input.text.toString().trim()

            Log.e(ContentValues.TAG, "Category : " + categoryName)

            if (categoryName != "") {

                val dbManager = DBManagerCategory(context)
                dbManager.insert(categoryName)

                recycleUpdate.isCategoryAdded(true)

                alertDialog.dismiss()
            } else {
                toastMessage(context, context.getString(R.string.please_enter_category_to_add))
            }
        })
    })

    alertDialog.show()
}

/**
 * dialog to update category
 * */
fun dialogUpdateCategory(context: Context, id: Int, categoryUpdate: CategoryUpdate) {

    val dbManager = DBManagerCategory(context)
    val catName = dbManager.getCategoryName(id)

    val li = LayoutInflater.from(context)
    val promptsView = li.inflate(R.layout.alert_dialog_update_category, null)

    val alert = AlertDialog.Builder(context)
    alert.setView(promptsView)

    val input: EditText = promptsView.findViewById(R.id.edtUpdateCat) as EditText

    input.setText(catName)
    input.setSelection(input.text.length)

    alert.setPositiveButton(R.string.update, { _, _ -> })

    alert.setNegativeButton(R.string.cancel, { _, _ -> })
    val alertDialog = alert.create()

    val categoryUpdated = categoryUpdate

    alertDialog.setOnShowListener({

        val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.setOnClickListener({

            val cat: String = input.text.toString().trim()

            Log.e(ContentValues.TAG, "Category : " + cat)

            if (cat != "") {
                if (cat != catName) {

                    dbManager.update(id, cat)

                    val mArrayList = dbManager.getCategoryList()
                    categoryUpdated.isCategoryUpdated(true, mArrayList)

                    alertDialog.dismiss()
                } else {
                    toastMessage(context, context.getString(R.string.please_edit_category_to_update))
                }
            } else {
                toastMessage(context, context.getString(R.string.please_enter_something_to_update))
            }
        })
    })

    alertDialog.show()
}

/**
 * dialog to delete category
 * */
fun dialogDeleteCategory(context: Context, id: Int, categoryDelete: CategoryDelete) {

    val dbManager = DBManagerCategory(context)

    val alert = AlertDialog.Builder(context)
    alert.setTitle("Delete Category")
    alert.setMessage("Do you want to delete this category?")

    alert.setPositiveButton(R.string.delete, { _, _ -> })

    alert.setNegativeButton(R.string.cancel, { _, _ -> })
    val alertDialog = alert.create()

    val categoryDeleted = categoryDelete

    alertDialog.setOnShowListener({

        val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.setOnClickListener({

            dbManager.delete(id)

            val mArrayList = dbManager.getCategoryList()
            categoryDeleted.isCategoryDeleted(true, mArrayList)

            alertDialog.dismiss()
        })
    })

    alertDialog.show()
}