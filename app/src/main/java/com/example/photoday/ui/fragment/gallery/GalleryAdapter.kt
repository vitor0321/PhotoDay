package com.example.photoday.ui.fragment.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.databinding.ItemGalleryFragmentBinding
import com.example.photoday.ui.adapter.extension.DiffCallback
import com.example.photoday.ui.databinding.data.ItemPhotoData
import com.example.photoday.ui.model.item.ItemPhoto

class GalleryAdapter(
    private val context: Context,
    private val items: List<ItemPhoto>,
    var onItemClickListener: (selectItem: ItemPhoto) -> Unit = {},
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemGalleryFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val viewDataBinding: ItemGalleryFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        private lateinit var itemPhoto: ItemPhoto

        override fun onClick(view: View?) {
            onItemClickListener(itemPhoto)
        }

        init {
            viewDataBinding.clickCardView = this
        }

        fun bind(item: ItemPhoto) {
            this.itemPhoto = item
            viewDataBinding.itemGallery = ItemPhotoData(item)
        }
    }
}