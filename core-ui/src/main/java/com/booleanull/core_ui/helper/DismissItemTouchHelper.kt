package com.booleanull.core_ui.helper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DismissItemTouchHelper(private val onItemDismissListener: OnItemDismissListener) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.START + ItemTouchHelper.END) // TODO: CHECK THIS
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemDismissListener.onItemDismiss(viewHolder.adapterPosition)
    }

    interface OnItemDismissListener {

        fun onItemDismiss(position: Int)
    }
}