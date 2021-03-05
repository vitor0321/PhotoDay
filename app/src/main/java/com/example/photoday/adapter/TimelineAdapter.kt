package com.example.photoday.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class TimelineAdapter(
    private val items: List<ItemPhoto>,
    private val listen: (item: ItemPhoto) -> Unit,
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

        holder.dateCalendar.text = item.dateCalendar
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = binding.imageTimeline
        val dateCalendar = binding.datePhotoTextView
    }
}