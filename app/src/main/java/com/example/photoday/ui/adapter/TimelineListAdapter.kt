package com.example.photoday.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.photoday.R
import com.example.photoday.data.modelAdapter.TimelineItemPhotos
import com.example.photoday.databinding.ItemTimelineFragmentBinding

class TimelineListAdapter(private var items: List<TimelineItemPhotos>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _binding: ItemTimelineFragmentBinding? = null
    private val binding: ItemTimelineFragmentBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        _binding = ItemTimelineFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimelineViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimelineViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class TimelineViewHolder(itemView: View, binding: ItemTimelineFragmentBinding) : RecyclerView.ViewHolder(itemView) {
        private val dateTimeline = binding.datePhotoTextView
        private val photoTimeline = binding.imageTimeline

        fun bind(timelineAdapter: TimelineItemPhotos) {
            dateTimeline.text = timelineAdapter.date

            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_item_photo)
                    .error(R.drawable.ic_item_photo)

            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                .load(timelineAdapter.photo)
                .into(photoTimeline)
        }
    }
}