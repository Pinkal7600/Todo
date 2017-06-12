package com.pinkal.todo.utils.views.recyclerview.itemdrag

/**
 * Created by Pinkal on 12/6/17.
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}