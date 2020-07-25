package com.booleanull.core_ui.adapter

import android.view.ViewGroup

interface ViewHolderFactory<T, R> {

    fun create(parent: ViewGroup, viewType: Int): GenericViewHolder<T, R>
}