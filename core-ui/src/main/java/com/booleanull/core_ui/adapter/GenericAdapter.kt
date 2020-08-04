package com.booleanull.core_ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.booleanull.core_ui.helper.DismissItemTouchHelper

open class GenericAdapter<T, R>(private val viewHolderFactory: ViewHolderFactory<T, R>) :
    RecyclerView.Adapter<GenericViewHolder<T, R>>(),
    DismissItemTouchHelper.OnItemDismissListener {

    var dataList = mutableListOf<T>()
        set(value) {
            diffUtil?.let { diffUtil ->
                val diffResult =
                    DiffUtil.calculateDiff(GenericDiffUtil(field, value, diffUtil))
                field = value
                diffResult.dispatchUpdatesTo(this)
            } ?: also {
                field = value
                notifyDataSetChanged()
            }
        }

    private var onItemClickListener: OnItemClickListener<R>? = null
    private var diffUtil: GenericItemDiff<T>? = null
    private var onItemDismissListener: DismissItemTouchHelper.OnItemDismissListener? = null

    final override fun onItemDismiss(position: Int) {
        onItemDismissListener?.onItemDismiss(position)
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericViewHolder<T, R> =
        viewHolderFactory.create(parent, viewType)

    final override fun getItemCount() =
        dataList.size

    final override fun onBindViewHolder(holder: GenericViewHolder<T, R>, position: Int) {
        holder.bind(dataList[position], onItemClickListener)
    }

    final override fun getItemViewType(position: Int): Int {
        return getItemViewType(
            position,
            dataList[position]
        )
    }

    open fun getItemViewType(position: Int, obj: T) = 0

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<R>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setDiffUtil(diffUtil: GenericItemDiff<T>) {
        this.diffUtil = diffUtil
    }

    fun setOnItemDismissListener(onItemDismissListener: DismissItemTouchHelper.OnItemDismissListener) {
        this.onItemDismissListener = onItemDismissListener
    }
}