package com.pinkal.todo.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.pinkal.todo.R
import com.pinkal.todo.`interface`.CategoryAdd
import com.pinkal.todo.database.manager.DBManagerCategory
import com.pinkal.todo.database.manager.DBManagerTask
import com.pinkal.todo.listener.onItemSelectedListener
import com.pinkal.todo.utils.dialogAddCategory
import com.pinkal.todo.utils.toastMessage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Pinkal on 24/5/17.
 */
class AddTaskActivity : AppCompatActivity(), View.OnClickListener, CategoryAdd, onItemSelectedListener.CategoryName {

    val TAG: String = MainActivity::class.java.simpleName

    val mActivity: Activity = this@AddTaskActivity

    var toolbar: Toolbar? = null

    var edtTitle: EditText? = null

    var edtTask: EditText? = null
    var edtSetDate: EditText? = null
    var edtSetTime: EditText? = null
    var spinnerCategory: Spinner? = null
    var imgAddCategory: ImageView? = null
    var imgCancelDate: ImageView? = null
    var imgCancelTime: ImageView? = null
    var relativeLayoutTime: RelativeLayout? = null

    private var myCalendar: Calendar? = null

    private var dateSetListener: DatePickerDialog.OnDateSetListener? = null
    private var timeSetListener: TimePickerDialog.OnTimeSetListener? = null
    //Final variable to save in database
    private var finalDate = ""

    private var finalTime = ""
    private var finalTitle = ""
    private var finalTask = ""
    private var finalCategoryName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        initialize()
    }


    /**
     * initializing views and data
     * */
    private fun initialize() {
        toolbar = findViewById(R.id.toolbarAddTask) as Toolbar

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        /**
         * Binding views
         * */
        edtTitle = findViewById(R.id.edtTitle) as EditText
        edtTask = findViewById(R.id.edtTask) as EditText
        edtSetDate = findViewById(R.id.edtSetDate) as EditText
        edtSetTime = findViewById(R.id.edtSetTime) as EditText
        spinnerCategory = findViewById(R.id.spinnerCategory) as Spinner
        imgAddCategory = findViewById(R.id.imgAddCategory) as ImageView
        imgCancelDate = findViewById(R.id.imgCancelDate) as ImageView
        imgCancelTime = findViewById(R.id.imgCancelTime) as ImageView
        relativeLayoutTime = findViewById(R.id.relativeLayoutTime) as RelativeLayout

        /**
         * click listener
         * */
        edtSetDate!!.setOnClickListener(this)
        edtSetTime!!.setOnClickListener(this)
        imgCancelDate!!.setOnClickListener(this)
        imgCancelTime!!.setOnClickListener(this)
        imgAddCategory!!.setOnClickListener(this)

        loadDataInSpinner()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * inflating actionbar menu
     * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * actionbar clicks
     * */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId

        when (id) {
            R.id.action_done -> {
                addTask()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Add Task in database
     * */
    private fun addTask() {

        finalTitle = edtTitle!!.text.toString().trim()
        finalTask = edtTask!!.text.toString().trim()

        val dbManager = DBManagerTask(mActivity)

        if (finalTitle != "") {
            if (finalTask != "") {
                if (finalDate != "") {
                    if (finalTime != "") {

                        //if time enter
                        Log.e(TAG, "Title : " + finalTitle + "\nTask : " + finalTask +
                                "\nDate : " + finalDate + "\nTime : " + finalTime +
                                "\nCategory : " + finalCategoryName)

                        dbManager.insert(finalTitle, finalTask, finalCategoryName, finalDate, finalTime)

                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {

                        //if only date enter
                        Log.e(TAG, "Title : " + finalTitle + "\nTask : " + finalTask +
                                "\nDate : " + finalDate + "\nCategory : " + finalCategoryName)

                        dbManager.insert(finalTitle, finalTask, finalCategoryName, finalDate)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                } else {

                    //if date not enter
                    Log.e(TAG, "Title : " + finalTitle + "Task : " + finalTask +
                            "Category : " + finalCategoryName)

                    dbManager.insert(finalTitle, finalTask, finalCategoryName)
                    setResult(Activity.RESULT_OK)
                    finish()

                }

            } else {
                toastMessage(mActivity, getString(R.string.please_add_task))
            }
        } else {
            toastMessage(mActivity, getString(R.string.please_add_title))
        }
    }

    private fun loadDataInSpinner() {

        val dbManager = DBManagerCategory(mActivity)
        var labels = dbManager.getListOfCategory()

        if (labels.isEmpty()) {
            val arrayList: ArrayList<String> = ArrayList()
            arrayList.add("No category added")
            labels = arrayList
        }

        val dataAdapter = ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, labels)

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        Collections.sort(labels)

        spinnerCategory!!.adapter = dataAdapter

        spinnerCategory!!.onItemSelectedListener = onItemSelectedListener(this)
    }

    override fun spinnerCatName(categoryName: String) {
        if (categoryName != "No category added") {
            if (categoryName != "") {
                finalCategoryName = categoryName
            }
        }

    }

    /**
     * Views clicks
     * */
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.edtSetDate -> {
                dateAndTime()
                setDate()
            }
            R.id.edtSetTime -> {
                dateAndTime()
                setTime()
            }
            R.id.imgCancelDate -> {
                if (edtSetDate != null) {
                    edtSetDate!!.setText("")
                    finalDate = ""
                    imgCancelDate!!.visibility = View.GONE
                    if (relativeLayoutTime!!.visibility == View.VISIBLE) {
                        relativeLayoutTime!!.visibility = View.GONE
                        if (edtSetTime != null) {
                            edtSetTime!!.setText("")
                            finalTime = ""
                            imgCancelTime!!.visibility = View.GONE
                        }
                    }
                }
            }
            R.id.imgCancelTime -> {
                if (edtSetTime != null) {
                    edtSetTime!!.setText("")
                    finalTime = ""
                    imgCancelTime!!.visibility = View.GONE
                }
            }
            R.id.imgAddCategory -> {
                dialogAddCategory(mActivity, this)
            }
        }
    }

    /**
     * current Date and Time initialize
     * */
    private fun dateAndTime() {

        myCalendar = Calendar.getInstance()

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar!!.set(Calendar.YEAR, year)
            myCalendar!!.set(Calendar.MONTH, monthOfYear)
            myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelDate()
        }

        timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            myCalendar!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar!!.set(Calendar.MINUTE, minute)
            updateLabelTime()
        }

    }

    /**
     * @DatePickerDialog for selecting date
     * */
    private fun setDate() {

        val datePickerDialog = DatePickerDialog(this, dateSetListener, myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH), myCalendar!!.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()

    }


    /**
     * @TimePickerDialog for selecting time
     * */
    private fun setTime() {
        val timePickerDialog = TimePickerDialog(this, timeSetListener, myCalendar!!.get(Calendar.HOUR_OF_DAY),
                myCalendar!!.get(Calendar.MINUTE), false)
        timePickerDialog.show()
    }

    /**
     * UI Update of time
     * */
    private fun updateLabelTime() {

        val myFormat = "HH:mm"  // HH:mm:ss
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        finalTime = sdf.format(myCalendar!!.time)


        val myFormat2 = "h:mm a"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
        edtSetTime!!.setText(sdf2.format(myCalendar!!.time))

        imgCancelTime!!.visibility = View.VISIBLE
    }


    /**
     * UI Update of time
     * */
    private fun updateLabelDate() {

        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        finalDate = sdf.format(myCalendar!!.time)


        val myFormat2 = "EEE, d MMM yyyy"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
        edtSetDate!!.setText(sdf2.format(myCalendar!!.time))

        relativeLayoutTime!!.visibility = View.VISIBLE
        imgCancelDate!!.visibility = View.VISIBLE
    }

    override fun isCategoryAdded(isAdded: Boolean) {
        if (isAdded) {
            loadDataInSpinner()
        }
    }

}


