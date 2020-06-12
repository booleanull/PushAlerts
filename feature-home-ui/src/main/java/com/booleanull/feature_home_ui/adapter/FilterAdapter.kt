package com.booleanull.feature_home_ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.booleanull.core_ui.ItemTouchSwipeHelper
import com.booleanull.feature_home_ui.R
import kotlinx.android.synthetic.main.cell_filter.view.*

class FilterAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchSwipeHelper.ItemTouchSwipeHelperAdapter {

    var filters: MutableList<String> = mutableListOf()

    var delegate: Delegate? = null
    interface Delegate {
        fun onSwipeDismiss(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_filter, parent, false))
    }

    override fun getItemCount(): Int = filters.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FilterViewHolder).bind(filters[holder.adapterPosition])
    }

    override fun onItemDismiss(position: Int) {
        delegate?.onSwipeDismiss(position)
    }

    private inner class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {
            with(itemView) {
                textView.text = item
            }
        }
    }

    class FilterDiffUtilCallback(
        private val oldList: List<String>,
        private val newList: List<String>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldProduct = oldList[oldItemPosition]
            val newProduct = newList[newItemPosition]
            return oldProduct == newProduct
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldProduct = oldList[oldItemPosition]
            val newProduct = newList[newItemPosition]
            return oldProduct == newProduct
        }
    }
}