package com.example.photoday.ui.databinding.data

import androidx.lifecycle.MutableLiveData
import com.example.photoday.ui.model.item.ItemNote

class ItemNoteData(
    private var itemNote: ItemNote = ItemNote(),
    val dateData: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemNote.date
    },
    val titleData: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemNote.title
    },
    val noteData: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemNote.note
    }
) {
    fun setItemNotaData(note: ItemNote) {
        this.itemNote = note
        dateData.postValue(this.itemNote.date)
        titleData.postValue(this.itemNote.title)
        noteData.postValue(this.itemNote.note)
    }
}