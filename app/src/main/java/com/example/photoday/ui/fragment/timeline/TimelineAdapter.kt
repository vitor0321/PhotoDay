package com.example.photoday.ui.fragment.timeline

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.example.photoday.ui.databinding.data.ItemPhotoData
import com.example.photoday.ui.model.item.ItemPhoto

class TimelineAdapter(
    private val context: Context,
    private val items: MutableList<ItemPhoto> = mutableListOf(),
    var onItemClickListener: (selectItem: ItemPhoto) -> Unit = {},
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemTimelineFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding).also {
            viewDataBinding.lifecycleOwner = it
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.stateRegistry(Lifecycle.State.STARTED)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stateRegistry(Lifecycle.State.DESTROYED)
    }

    fun update(newItem: List<ItemPhoto>) {
        notifyItemRangeRemoved(0, items.size)
        this.items.clear()
        this.items.addAll(newItem)
        notifyItemRangeInserted(0, items.size)
    }

    inner class ViewHolder(private val viewDataBinding: ItemTimelineFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener, LifecycleOwner {

        private lateinit var itemPhoto: ItemPhoto
        private val registry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = registry

        override fun onClick(view: View?) {
            if (::itemPhoto.isInitialized) {
                onItemClickListener(itemPhoto)
            }
        }

        init {
            stateRegistry(Lifecycle.State.INITIALIZED)
            viewDataBinding.clickCardView = this
        }

        fun bind(item: ItemPhoto) {
            this.itemPhoto = item
            viewDataBinding.itemTimeline = ItemPhotoData(item)
        }

        fun stateRegistry(state: Lifecycle.State) {
            registry.run { state }
        }
    }
}


