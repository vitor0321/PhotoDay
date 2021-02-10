package com.example.photoday.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.photoday.R
import com.example.photoday.data.modelAdapter.GalleryItemPhotos
import com.example.photoday.databinding.ItemGalleryFragmentBinding

class GalleryListAdapter(private var items: List<GalleryItemPhotos>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemGalleryFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemGalleryFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GalleryViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class GalleryViewHolder(itemView: View, binding: ItemGalleryFragmentBinding) : RecyclerView.ViewHolder(itemView) {
        private val photoGallery = binding.imageGallery

        fun bind(galleryAdapter : GalleryItemPhotos) {

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_item_photo)
                .error(R.drawable.ic_item_photo)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(galleryAdapter.photo)
                .into(photoGallery)
        }
    }
}