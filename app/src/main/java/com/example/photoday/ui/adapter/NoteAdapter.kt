package com.example.photoday.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
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
        return ViewHolder(viewDataBinding).also {
            viewDataBinding.lifecycleOwner = it
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.stateRegistry(Lifecycle.State.STARTED)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stateRegistry(Lifecycle.State.DESTROYED)
    }

    inner class ViewHolder(private val viewDataBinding: ItemNoteFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener, LifecycleOwner {

        private lateinit var itemNote: ItemNote
        private val registry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = registry

        override fun onClick(view: View?) {
            if (::itemNote.isInitialized) {
                onItemClickListener(itemNote)
            }
        }

        init {
            stateRegistry(Lifecycle.State.INITIALIZED)
            viewDataBinding.clickCardView = this
        }

        fun bind(item: ItemNote) {
            this.itemNote = item
            viewDataBinding.itemNote = ItemNoteData(item)
        }

        fun stateRegistry(state: Lifecycle.State) {
            registry.run { state }
        }
    }
}