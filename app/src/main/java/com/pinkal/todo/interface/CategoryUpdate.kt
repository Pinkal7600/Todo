package com.pinkal.todo.`interface`

import com.pinkal.todo.model.CategoryModel

/**
 * Created by Pinkal on 26/5/17.
 */
interface CategoryUpdate {
    fun isCategoryUpdated(isUpdated: Boolean, mArrayList: ArrayList<CategoryModel>)
}