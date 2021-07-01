package org.booleanull.feature_home_ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.cell_application.view.*
import org.booleanull.core.entity.Application
import org.booleanull.core_ui.adapter.GenericViewHolder
import org.booleanull.core_ui.adapter.OnItemClickListener
import org.booleanull.core_ui.adapter.ViewHolderFactory
import org.booleanull.feature_home_ui.R

internal class ApplicationViewHolderFactory :
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
                    notifyImageView.isVisible = item.hasAlarm
                    favoriteImageView.isVisible = item.isFavorite
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