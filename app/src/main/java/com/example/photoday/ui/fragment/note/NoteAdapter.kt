package com.example.photoday.ui.fragment.note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoday.databinding.ItemNoteFragmentBinding
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.model.item.ItemNote

class NoteAdapter(
    private val context: Context,
    private val items: List<ItemNote>,
    var onItemClickListener: (selectNota: ItemNote) -> Unit = {}
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemNoteFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val viewDataBinding: ItemNoteFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        private lateinit var itemNote: ItemNote

        override fun onClick(view: View?) {
            if (::itemNote.isInitialized){
                onItemClickListener(itemNote)
            }
        }

        init {
            viewDataBinding.clickCardView = this
        }

        fun bind(item: ItemNote) {
            this.itemNote = item
            viewDataBinding.itemNote = ItemNoteData(item)
        }
    }
}