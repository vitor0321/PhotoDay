package com.example.photoday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.R
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemGalleryFragmentBinding
import com.example.photoday.databinding.ItemTimelineFragmentBinding

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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ItemPhoto) {
            binding.run {
                imageTimeline.setImageResource(item.photo)
                datePhotoTextView.text = item.date
            }
        }
    }
}