package com.example.photoday.ui.fragment.timeline

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
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.example.photoday.ui.databinding.data.ItemPhotoData
import com.example.photoday.ui.model.item.ItemPhoto

class TimelineAdapter(
    private val context: Context,
    private val items: MutableList<ItemPhoto> = mutableListOf(),
    var onItemClickListener: (selectItem: ItemPhoto) -> Unit = {}
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemTimelineFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
>>>>>>> developing
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

<<<<<<< HEAD:app/src/main/java/com/example/photoday/ui/adapter/TimelineAdapter.kt
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
=======
    fun update(newItem: List<ItemPhoto>) {
        this.items.clear()
        this.items.addAll(newItem)
<<<<<<< HEAD
        notifyItemRangeInserted(0, items.size)
>>>>>>> developing:app/src/main/java/com/example/photoday/ui/fragment/timeline/TimelineAdapter.kt
=======
        notifyDataSetChanged()
>>>>>>> developing
    }

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
            viewDataBinding.itemTimeline = ItemPhotoData(item)
        }
<<<<<<< HEAD:app/src/main/java/com/example/photoday/ui/adapter/TimelineAdapter.kt

        fun stateRegistry(state: Lifecycle.State) {
            registry.run { state }
>>>>>>> developing
        }
=======
>>>>>>> developing:app/src/main/java/com/example/photoday/ui/fragment/timeline/TimelineAdapter.kt
    }
}


<<<<<<< HEAD
=======

<<<<<<< HEAD:app/src/main/java/com/example/photoday/ui/adapter/TimelineAdapter.kt
>>>>>>> developing
=======

>>>>>>> developing:app/src/main/java/com/example/photoday/ui/fragment/timeline/TimelineAdapter.kt
