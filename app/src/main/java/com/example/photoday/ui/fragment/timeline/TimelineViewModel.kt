package com.example.photoday.ui.fragment.timeline

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.data.modelAdapter.TimelineItemPhotos

class TimelineViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<TimelineItemPhotos>> = MutableLiveData()

    fun createPushPhotos(date: String, image: Uri) {
        photosLiveData.value = listOf(TimelineItemPhotos(date, image))
    }
}