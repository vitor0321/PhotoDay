package com.example.photoday.ui.databinding.data

import androidx.lifecycle.MutableLiveData
import com.example.photoday.ui.model.item.ItemNavigation

class ItemNavigationData(
    var itemNavigation: ItemNavigation = ItemNavigation(),
    val checkedGallery: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = itemNavigation.isCheckedGallery
    }
) {
    fun setItemNavigationData(itemNavigation: ItemNavigation?) {
        itemNavigation?.let { this.itemNavigation = itemNavigation }
        itemNavigation?.isCheckedGallery.let { checkedGallery.postValue(this.itemNavigation.isCheckedGallery) }
    }

    fun getItemNavigationData(): ItemNavigation? {
        return this.itemNavigation.copy(
            isCheckedGallery = checkedGallery.value ?: return null
        )
    }
}