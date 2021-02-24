package com.example.photoday.ui.fragment.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.R
import com.example.photoday.adapter.modelAdapter.ItemPhoto

class GalleryViewModel : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<ItemPhoto>>()
    val data: LiveData<List<ItemPhoto>> = mutableLiveData

    init {
        loadData(createPullPhotos())
    }

    private fun loadData(data: List<ItemPhoto>) = mutableLiveData.postValue(data)

    private fun createPullPhotos(): List<ItemPhoto> {
        return listOf(
            ItemPhoto("21/01/2021", R.drawable.ic_item_photo),
            ItemPhoto("22/01/2021", R.drawable.ic_item_photo),
            ItemPhoto("23/01/2021", R.drawable.ic_item_photo),
            ItemPhoto("24/01/2021", R.drawable.ic_item_photo),
            ItemPhoto("25/01/2021", R.drawable.ic_item_photo),
            ItemPhoto("26/01/2021", R.drawable.ic_item_photo),
            ItemPhoto("27/01/2021", R.drawable.ic_item_photo)
        )
    }

}