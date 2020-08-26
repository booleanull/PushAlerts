package org.booleanull.core_ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder<T, R>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T, onItemClickListener: OnItemClickListener<R>?)
}