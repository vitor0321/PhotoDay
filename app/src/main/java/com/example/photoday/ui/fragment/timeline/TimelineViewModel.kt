package com.example.photoday.ui.fragment.timeline

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.data.modelAdapter.TimelineItemPhotos
import com.example.photoday.repository.BaseRepositoryPhoto.baseRepositoryUploadImageToStorage

class TimelineViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<TimelineItemPhotos>> = MutableLiveData()

    fun createPullPhotos(date: String, image: Uri) {
        photosLiveData.value = listOf(TimelineItemPhotos(date, image))
    }

    fun createPushPhoto(context: Context, date: String, curFile: Uri?) {
        baseRepositoryUploadImageToStorage(context, date, curFile)
    }
}