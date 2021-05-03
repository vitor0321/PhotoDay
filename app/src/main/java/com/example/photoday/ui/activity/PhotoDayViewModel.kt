package com.example.photoday.ui.activity

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryPhoto

class PhotoDayViewModel(
    private val repository: BaseRepositoryPhoto,
) : ViewModel() {

    fun createPushPhoto(dateCalendar: String, curFile: Uri?) =
        repository.baseRepositoryUploadImageToStorage(dateCalendar, curFile)

}