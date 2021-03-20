package com.example.photoday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.BR
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemGalleryFragmentBinding
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.squareup.picasso.Picasso

class TimelineAdapter(
    private val items: List<ItemPhoto>,
    private val listen: (item: ItemPhoto) -> Unit
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {
    private lateinit var binding: ItemTimelineFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemTimelineFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.photo).into(holder.imageView)
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = binding.imageTimeline
        private val date = binding.datePhotoTextView

        fun bind(item: ItemPhoto) {
            date.text = item.dateCalendar
            imageView.setOnClickListener { listen.invoke(item) }
        }
    }
}

