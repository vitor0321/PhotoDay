package com.example.photoday.ui.adapter.extension

import androidx.recyclerview.widget.DiffUtil
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto

object DiffCallback : DiffUtil.ItemCallback<ItemPhoto>() {
    override fun areItemsTheSame(
        oldItem: ItemPhoto,
        newItem: ItemPhoto
    ) = oldItem.dateCalendar == newItem.dateCalendar
    override fun areContentsTheSame(oldItem: ItemPhoto, newItem: ItemPhoto) = oldItem == newItem
}