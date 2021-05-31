package com.example.photoday.ui.databinding.data

import androidx.lifecycle.MutableLiveData
import com.example.photoday.ui.model.item.ItemNote

class ItemNoteData(
    private var itemNote: ItemNote = ItemNote(),
    val date: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemNote.date
    },
    val title: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemNote.title
    },
    val note: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemNote.note
    }
) {
    fun setItemNotaData(itemNote: ItemNote?) {
        itemNote?.let { this.itemNote = itemNote }
        itemNote?.date?.let { date.postValue(this.itemNote.date) }
        itemNote?.title?.let { title.postValue(this.itemNote.title) }
        itemNote?.note?.let { note.postValue(this.itemNote.note) }
    }
}