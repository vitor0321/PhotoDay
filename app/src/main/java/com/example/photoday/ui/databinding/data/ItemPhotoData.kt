package com.example.photoday.ui.databinding.data

import androidx.lifecycle.MutableLiveData
import com.example.photoday.ui.model.adapter.ItemPhoto

class ItemPhotoData(
    private var itemPhoto: ItemPhoto = ItemPhoto(),
    val dateCalendar: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemPhoto.dateCalendar
    },
    val photo: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemPhoto.photo
    },

    ) {
    fun setItemPhotoData(itemPhoto: ItemPhoto) {
        this.itemPhoto = itemPhoto
        dateCalendar.postValue(this.itemPhoto.dateCalendar)
        photo.postValue(this.itemPhoto.photo)
    }

    fun getComponentsData(): ItemPhoto? {
        return this.itemPhoto.copy(
            dateCalendar = dateCalendar.value ?: return null,
            photo = photo.value ?: return null,
        )
    }
}