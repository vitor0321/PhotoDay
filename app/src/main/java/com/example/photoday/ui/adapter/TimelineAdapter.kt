package com.example.photoday.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.BR
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.ItemTimelineFragmentBinding
import com.squareup.picasso.Picasso

class TimelineAdapter(
    private val items: List<ItemPhoto>,
    private val listen: (item: ItemPhoto) -> Unit,
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {
    private lateinit var binding: ItemTimelineFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        binding = ItemTimelineFragmentBinding.inflate(inflate, parent, false)
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

        fun bind(item: ItemPhoto) {
            imageView.setOnClickListener { listen.invoke(item) }
        }
    }
}


