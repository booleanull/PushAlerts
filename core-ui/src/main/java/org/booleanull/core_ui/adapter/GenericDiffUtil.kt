package org.booleanull.core_ui.adapter

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val itemDiff: GenericItemDiff<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemDiff.areItemsTheSame(oldItems, newItems, oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemDiff.areContentsTheSame(oldItems, newItems, oldItemPosition, newItemPosition)
}