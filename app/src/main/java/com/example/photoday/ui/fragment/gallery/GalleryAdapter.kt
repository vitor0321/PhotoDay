package com.example.photoday.ui.fragment.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
<<<<<<< HEAD:app/src/main/java/com/example/photoday/ui/adapter/GalleryAdapter.kt
<<<<<<< HEAD
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
=======
import com.example.photoday.ui.adapter.extension.DiffCallback
<<<<<<< HEAD
import com.example.photoday.ui.model.adapter.ItemPhoto
>>>>>>> developing
=======
import com.example.photoday.ui.model.item.ItemPhoto
>>>>>>> developing
import com.example.photoday.databinding.ItemGalleryFragmentBinding
import com.squareup.picasso.Picasso

class GalleryAdapter(
    private val items: List<ItemPhoto>,
    private val listen: (item: ItemPhoto) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private lateinit var binding: ItemGalleryFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        binding =
            ItemGalleryFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
=======
import com.example.photoday.databinding.ItemGalleryFragmentBinding
import com.example.photoday.ui.databinding.data.ItemPhotoData
import com.example.photoday.ui.model.item.ItemPhoto

class GalleryAdapter(
    private val context: Context,
    private val items: MutableList<ItemPhoto> = mutableListOf(),
    var onItemClickListener: (selectItem: ItemPhoto) -> Unit = {},
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemGalleryFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding)
>>>>>>> developing:app/src/main/java/com/example/photoday/ui/fragment/gallery/GalleryAdapter.kt
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.photo).into(holder.imageView)

        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

<<<<<<< HEAD:app/src/main/java/com/example/photoday/ui/adapter/GalleryAdapter.kt
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = binding.imageGallery

        fun bind(item: ItemPhoto) {
            imageView.setOnClickListener { listen.invoke(item) }
=======
    fun update(newItem: List<ItemPhoto>) {
        notifyItemRangeRemoved(0, items.size)
        this.items.clear()
        this.items.addAll(newItem)
        notifyItemRangeInserted(0, items.size)
    }

    inner class ViewHolder(private val viewDataBinding: ItemGalleryFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        private lateinit var itemPhoto: ItemPhoto

        override fun onClick(view: View?) {
            onItemClickListener(itemPhoto)
        }

        init {
            viewDataBinding.clickCardView = this
        }

        fun bind(item: ItemPhoto) {
            this.itemPhoto = item
            viewDataBinding.itemGallery = ItemPhotoData(item)
>>>>>>> developing:app/src/main/java/com/example/photoday/ui/fragment/gallery/GalleryAdapter.kt
        }
    }
}