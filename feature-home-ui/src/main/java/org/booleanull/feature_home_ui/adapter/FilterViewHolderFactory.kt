package org.booleanull.feature_home_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cell_filter.view.*
import org.booleanull.core_ui.adapter.GenericViewHolder
import org.booleanull.core_ui.adapter.OnItemClickListener
import org.booleanull.core_ui.adapter.ViewHolderFactory
import org.booleanull.feature_home_ui.R

internal class FilterViewHolderFactory : ViewHolderFactory<String, Int> {
    override fun create(parent: ViewGroup, viewType: Int): GenericViewHolder<String, Int> =
        object : GenericViewHolder<String, Int>(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_filter, parent, false)
        ) {
            override fun bind(item: String, onItemClickListener: OnItemClickListener<Int>?) {
                with(itemView) {
                    textView.text = item
                    setOnClickListener { onItemClickListener?.onItemClick(adapterPosition) }
                }
            }
        }
}