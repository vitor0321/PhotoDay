package com.example.photoday.ui.adapter

<<<<<<< HEAD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.BR
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.squareup.picasso.Picasso

class TimelineAdapter(
    private val items: List<ItemPhoto>,
    private val listen: (item: ItemPhoto) -> Unit,
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {
    private lateinit var binding: ItemTimelineFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        binding = ItemTimelineFragmentBinding.inflate(inflate, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.photo).into(holder.imageView)
=======
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.example.photoday.ui.adapter.extension.DiffCallback
import com.example.photoday.ui.databinding.data.ItemPhotoData
import com.example.photoday.ui.model.item.ItemPhoto

class TimelineAdapter(
    private val context: Context,
    private val items: List<ItemPhoto>,
    var onItemClickListener: (selectItem: ItemPhoto) -> Unit = {},
) : ListAdapter<ItemPhoto, TimelineAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemTimelineFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding).also {
            viewDataBinding.lifecycleOwner = it
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
>>>>>>> developing
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

<<<<<<< HEAD
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = binding.imageTimeline

        fun bind(item: ItemPhoto) {
            imageView.setOnClickListener { listen.invoke(item) }
=======
    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.stateRegistry(Lifecycle.State.STARTED)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stateRegistry(Lifecycle.State.DESTROYED)
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
>>>>>>> developing
        }
    }
}


<<<<<<< HEAD
=======

>>>>>>> developing
