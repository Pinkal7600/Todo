package com.pinkal.todo.utils

/**
 * Created by Pinkal on 25/5/17.
 *
 * @Constant
 * All constant Variable are declare here
 * and all are companion object so it can
 * be access directly without reference
 */
class Constant {
    companion object {

        /**************** Database ****************/

        // Database helper
        val DB_NAME = "Todo.db"
        val DB_VERSION = 1

        // Table name
        val TABLE_CATEGORY = "CATEGORY"

        // Table columns for CATEGORY
        val ID = "id"
        val CATEGORY_NAME = "category_name"

    }
}