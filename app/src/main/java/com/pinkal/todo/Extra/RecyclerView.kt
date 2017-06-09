package com.pinkal.todo.Extra

/**
 * Created by Pinkal on 9/6/17.
 */

/**override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

    val icon: Bitmap
    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

        val itemView = viewHolder.itemView
        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
        val width = height / 3

        if (dX > 0) {
            paint.color = Color.parseColor(getString(R.color.green))

            val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left.toFloat() + dX, itemView.bottom.toFloat())
            canvas.drawRect(background, paint)

            icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_check_white_png)

            val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
            canvas.drawBitmap(icon, null, icon_dest, paint)
        } else {
            paint.color = Color.parseColor(getString(R.color.red))

            val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            canvas.drawRect(background, paint)

            icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_delete_white_png)

            val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
            canvas.drawBitmap(icon, null, icon_dest, paint)
        }
    }
    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
}

 */