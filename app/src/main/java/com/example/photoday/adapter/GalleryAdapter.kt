package com.example.photoday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemGalleryFragmentBinding

class GalleryAdapter(
    private val items: List<ItemPhoto>,
    private val listen: (item: ItemPhoto) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private lateinit var binding: ItemGalleryFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        binding =
            ItemGalleryFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ItemPhoto) {
            binding.imageGallery.setImageResource(item.photo)
        }
    }
}