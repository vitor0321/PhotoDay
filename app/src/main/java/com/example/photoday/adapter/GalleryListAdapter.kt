package com.example.photoday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.photoday.R
import com.example.photoday.adapter.modelAdapter.GalleryAdapter
import kotlinx.android.synthetic.main.item_gallery_fragment.view.*
import java.util.*

class GalleryListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<GalleryAdapter> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimelineListAdapter.TimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gallery_fragment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GalleryAdapterViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class GalleryAdapterViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoGallery: AppCompatImageView = itemView.image_gallery

        fun bind(galleryAdapter : GalleryAdapter) {

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