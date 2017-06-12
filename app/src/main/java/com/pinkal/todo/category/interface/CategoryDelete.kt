package com.pinkal.todo.category.`interface`

import com.pinkal.todo.category.model.CategoryModel

/**
 * Created by Pinkal on 26/5/17.
 */
interface CategoryDelete {
    fun isCategoryDeleted(isDeleted: Boolean, mArrayList: ArrayList<CategoryModel>)
}