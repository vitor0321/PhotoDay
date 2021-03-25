package com.example.photoday.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.ui.adapter.extension.DiffCallback
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemGalleryFragmentBinding
import com.squareup.picasso.Picasso

class GalleryAdapter(
    private val items: List<ItemPhoto>,
    private val listen: (item: ItemPhoto) -> Unit
) : ListAdapter<ItemPhoto, GalleryAdapter.ViewHolder>(DiffCallback) {
    private lateinit var binding: ItemGalleryFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        binding =
            ItemGalleryFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.photo).into(holder.imageView)

        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = binding.imageGallery

        fun bind(item: ItemPhoto) {
            imageView.setOnClickListener { listen.invoke(item) }
        }
    }
}