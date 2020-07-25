package com.booleanull.core_ui.adapter

interface GenericItemDiff<T> {

    fun areItemsTheSame(oldItems: List<T>, newItems: List<T>, oldItemPosition: Int, newItemPosition: Int): Boolean

    fun areContentsTheSame(oldItems: List<T>, newItems: List<T>, oldItemPosition: Int, newItemPosition: Int): Boolean
}