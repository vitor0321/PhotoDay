package com.example.photoday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.photoday.R
import com.example.photoday.adapter.modelAdapter.TimelineAdapter
import kotlinx.android.synthetic.main.item_timeline_fragment.view.*
import java.util.*

class TimelineListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<TimelineAdapter> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_timeline_fragment, parent, false)
        )
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

    fun submitList(timelineList: List<TimelineAdapter>) {
        items = timelineList
    }

    class TimelineViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTimeline: AppCompatTextView = itemView.date_photo_text_view
        private val photoTimeline: AppCompatImageView = itemView.image_timeline

        fun bind(timelineAdapter: TimelineAdapter) {
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