package com.example.photoday.ui.fragment.timeline

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.data.modelAdapter.TimelineItemPhotos

class TimelineViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<TimelineItemPhotos>> = MutableLiveData()

    fun createPushPhotos(image: Uri) {
        photosLiveData.value = listOf(TimelineItemPhotos("01/01/2020", image))
    }
}