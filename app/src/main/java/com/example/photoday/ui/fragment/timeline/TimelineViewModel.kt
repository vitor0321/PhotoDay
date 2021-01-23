package com.example.photoday.ui.fragment.timeline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.R
import com.example.photoday.data.modelAdapter.TimelineItemPhotos

class TimelineViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<TimelineItemPhotos>> = MutableLiveData()
    fun getPhotos() {
        photosLiveData.value = createFakeGetPhotos()
    }
    //isso é profissório para teste, pois vamos pegar essa lista do nosso BD
    private fun createFakeGetPhotos(): List<TimelineItemPhotos> {
        return listOf(
            TimelineItemPhotos("01/01/2020", R.drawable.ic_item_photo),
            TimelineItemPhotos("01/01/2020", R.drawable.ic_item_photo),
            TimelineItemPhotos("01/01/2020", R.drawable.ic_item_photo),
            TimelineItemPhotos("01/01/2020", R.drawable.ic_item_photo),
            TimelineItemPhotos("01/01/2020", R.drawable.ic_item_photo),
            TimelineItemPhotos("01/01/2020", R.drawable.ic_item_photo),
            TimelineItemPhotos("01/01/2020", R.drawable.ic_item_photo)
        )
    }
}