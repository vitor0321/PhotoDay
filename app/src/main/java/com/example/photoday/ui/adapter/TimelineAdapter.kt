package com.example.photoday.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.example.photoday.ui.adapter.extension.DiffCallback
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto

class TimelineAdapter(
    private val context: Context,
    private val items: List<ItemPhoto>,
    var onItemClickListener: (selectItem: ItemPhoto) -> Unit = {},
) : ListAdapter<ItemPhoto, TimelineAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemTimelineFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val viewDataBinding: ItemTimelineFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        private lateinit var itemPhoto: ItemPhoto

        override fun onClick(view: View?) {
            if (::itemPhoto.isInitialized) {
                onItemClickListener(itemPhoto)
            }
        }

        init {
            viewDataBinding.clickCardView = this
        }

        fun bind(item: ItemPhoto) {
            this.itemPhoto = item
            viewDataBinding.itemTimeline = item
        }
    }
}



