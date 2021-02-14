package com.example.photoday.ui.fragment.timeline

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.data.modelAdapter.TimelineItemPhotos
import com.example.photoday.repository.firebase.CheckUserFirebase
import com.example.photoday.repository.user.UserFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimelineViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<TimelineItemPhotos>> = MutableLiveData()

    fun createPushPhotos(image: Uri) {
        photosLiveData.value = listOf(TimelineItemPhotos("01/01/2020", image))
    }
}