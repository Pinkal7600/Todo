package com.pinkal.todo.utils

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.pinkal.todo.R
import com.pinkal.todo.`interface`.CategoryAdd
import com.pinkal.todo.`interface`.CategoryDelete
import com.pinkal.todo.`interface`.CategoryUpdate
import com.pinkal.todo.database.manager.DBManagerCategory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Pinkal on 25/5/17.
 */
class CommanUtils {

    companion object {

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

            alert.setPositiveButton("Add", { _, _ -> })

            alert.setNegativeButton("Cancel", { _, _ -> })
            val alertDialog = alert.create()

            val recycleUpdate = categoryAdd

            alertDialog.setOnShowListener({

                val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener({

                    val cat: String = input.text.toString().trim()

                    Log.e(TAG, "Category : " + cat)

                    if (cat != "") {

                        val dbManager = DBManagerCategory(context)
                        val contentValues = ContentValues()
                        contentValues.put(Constant.CATEGORY_NAME, cat)
                        dbManager.insert(contentValues)

                        recycleUpdate.isCategoryAdded(true)

                        Toast.makeText(context, "Category add", Toast.LENGTH_SHORT).show()

                        alertDialog.dismiss()
                    } else {
                        Toast.makeText(context, "Please enter category to add", Toast.LENGTH_SHORT).show()
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

            val li = LayoutInflater.from(context)
            val promptsView = li.inflate(R.layout.alert_dialog_update_category, null)

            val alert = AlertDialog.Builder(context)
            alert.setView(promptsView)

            val input: EditText = promptsView.findViewById(R.id.edtUpdateCat) as EditText

            input.setText(dbManager.getCategoryName(id))
            input.setSelection(input.text.length)

            alert.setPositiveButton("UPDATE", { _, _ -> })

            alert.setNegativeButton("Cancel", { _, _ -> })
            val alertDialog = alert.create()

            val categoryUpdate = categoryUpdate

            alertDialog.setOnShowListener({

                val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener({

                    val cat: String = input.text.toString().trim()

                    Log.e(TAG, "Category : " + cat)

                    if (cat != "") {

                        dbManager.update(id, cat)

                        val mArrayList = dbManager.getCategoryList()
                        categoryUpdate.isCategoryUpdated(true, mArrayList)

                        Toast.makeText(context, "Category Updated", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    } else {
                        Toast.makeText(context, "Please enter category to add", Toast.LENGTH_SHORT).show()
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

            alert.setPositiveButton("DELETE", { _, _ -> })

            alert.setNegativeButton("Cancel", { _, _ -> })
            val alertDialog = alert.create()

            val categoryDelete = categoryDelete

            alertDialog.setOnShowListener({

                val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener({

                    dbManager.delete(id)

                    val mArrayList = dbManager.getCategoryList()
                    categoryDelete.isCategoryDeleted(true, mArrayList)

                    Toast.makeText(context, "Category Deleted", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                })
            })

            alertDialog.show()
        }
    }
}