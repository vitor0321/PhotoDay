package com.example.photoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.BR
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemTimelineFragmentBinding

class TimelineAdapter(
    private val items: List<ItemPhoto>,
    var listenOnClick: (item: ItemPhoto) -> Unit = {},
) : ListAdapter<ItemPhoto, TimelineAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val viewDataBinding = ItemTimelineFragmentBinding.inflate(inflate, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val viewDataBinding: ItemTimelineFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        private lateinit var itemTimeline: ItemPhoto

        init {
            itemView.setOnClickListener {
                if (::itemTimeline.isInitialized) {
                    listenOnClick(itemTimeline)
                }
            }
        }

        fun bind(item: ItemPhoto) {
            this.itemTimeline = item
            viewDataBinding.setVariable(BR.itemTimeline, item)
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<ItemPhoto>() {
    override fun areItemsTheSame(
        oldItem: ItemPhoto,
        newItem: ItemPhoto,
    ) = oldItem.dateCalendar == newItem.dateCalendar

    override fun areContentsTheSame(oldItem: ItemPhoto, newItem: ItemPhoto) = oldItem == newItem
}

