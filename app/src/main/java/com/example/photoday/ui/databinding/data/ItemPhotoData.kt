package com.example.photoday.ui.databinding.data

import androidx.lifecycle.MutableLiveData
import com.example.photoday.ui.model.item.ItemPhoto

class ItemPhotoData(
    private var itemPhoto: ItemPhoto = ItemPhoto(),
    val date: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemPhoto.date
    },
    val photo: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = itemPhoto.photo
    },

    ) {
    fun setItemPhotoData(itemPhoto: ItemPhoto?) {
        itemPhoto?.let { this.itemPhoto = itemPhoto }
        itemPhoto?.date?.let { date.postValue(this.itemPhoto.date) }
        itemPhoto?.photo.let { photo.postValue(this.itemPhoto.photo) }
    }

    fun getComponentsData(): ItemPhoto? {
        return this.itemPhoto.copy(
            date = date.value ?: return null,
            photo = photo.value ?: return null,
        )
    }
}