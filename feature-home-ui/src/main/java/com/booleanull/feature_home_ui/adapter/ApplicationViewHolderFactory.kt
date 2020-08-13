package com.booleanull.feature_home_ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core.entity.Application
import com.booleanull.core_ui.adapter.GenericViewHolder
import com.booleanull.core_ui.adapter.OnItemClickListener
import com.booleanull.core_ui.adapter.ViewHolderFactory
import com.booleanull.feature_home_ui.R
import kotlinx.android.synthetic.main.cell_application.view.*

class ApplicationViewHolderFactory :
    ViewHolderFactory<Application, ApplicationViewHolderFactory.ApplicationItemClickData> {

    override fun create(parent: ViewGroup, viewType: Int) =
        object : GenericViewHolder<Application, ApplicationItemClickData>(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_application, parent, false)
        ) {
            override fun bind(
                item: Application,
                onItemClickListener: OnItemClickListener<ApplicationItemClickData>?
            ) {
                with(itemView) {
                    titleTextView.text = item.name
                    tvDescription.text = item.packageName
                    iconImageView.setImageDrawable(item.icon)
                    iconImageView.transitionName = item.packageName
                    setOnClickListener {
                        onItemClickListener?.onItemClick(
                            ApplicationItemClickData(
                                item,
                                iconImageView
                            )
                        )
                    }
                }
            }
        }

    data class ApplicationItemClickData(val application: Application, val view: View)
}