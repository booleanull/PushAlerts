package com.booleanull.feature_home_ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home_ui.R
import kotlinx.android.synthetic.main.cell_application.view.*

class ApplicationAdapter(private val onItemClickListener: (application: Application, image: ImageView) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var applications: List<Application> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ApplicationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_application, parent, false))
    }

    override fun getItemCount(): Int = applications.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ApplicationViewHolder).bind(applications[holder.adapterPosition])
    }

    private inner class ApplicationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: Application) {
            with(itemView) {
                titleTextView.text = item.name
                tvDescription.text = item.packageName
                iconImageView.setImageDrawable(item.icon)
                iconImageView.transitionName = item.packageName
                setOnClickListener {
                    onItemClickListener.invoke(item, iconImageView)
                }
            }
        }
    }

    class ApplicationDiffUtilCallback(
        private val oldList: List<Application>,
        private val newList: List<Application>
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
            return oldProduct.packageName == newProduct.packageName
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldProduct = oldList[oldItemPosition]
            val newProduct = newList[newItemPosition]
            return oldProduct.name == newProduct.name
        }
    }
}