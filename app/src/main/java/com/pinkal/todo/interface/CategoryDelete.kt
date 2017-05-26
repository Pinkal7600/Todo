package com.pinkal.todo.`interface`

import com.pinkal.todo.model.CategoryModel

/**
 * Created by Pinkal on 26/5/17.
 */
interface CategoryDelete {
    fun isCategoryDeleted(isDeleted: Boolean, mArrayList: ArrayList<CategoryModel>)
}