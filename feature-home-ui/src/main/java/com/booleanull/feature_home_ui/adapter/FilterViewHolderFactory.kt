package com.booleanull.feature_home_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.booleanull.core_ui.adapter.GenericViewHolder
import com.booleanull.core_ui.adapter.OnItemClickListener
import com.booleanull.core_ui.adapter.ViewHolderFactory
import com.booleanull.feature_home_ui.R
import kotlinx.android.synthetic.main.cell_filter.view.*

class FilterViewHolderFactory : ViewHolderFactory<String, Unit> {
    override fun create(parent: ViewGroup, viewType: Int): GenericViewHolder<String, Unit> =
        object : GenericViewHolder<String, Unit>(LayoutInflater.from(parent.context).inflate(R.layout.cell_filter, parent, false)) {
            override fun bind(item: String, onItemClickListener: OnItemClickListener<Unit>?) {
                with(itemView) {
                    textView.text = item
                }
            }
        }
}