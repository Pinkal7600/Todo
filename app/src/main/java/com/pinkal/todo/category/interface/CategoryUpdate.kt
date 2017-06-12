package com.pinkal.todo.category.`interface`

import com.pinkal.todo.category.model.CategoryModel

/**
 * Created by Pinkal on 26/5/17.
 */
interface CategoryUpdate {
    fun isCategoryUpdated(isUpdated: Boolean, mArrayList: ArrayList<CategoryModel>)
}