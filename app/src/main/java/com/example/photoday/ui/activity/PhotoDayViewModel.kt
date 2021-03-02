package com.example.photoday.ui.activity

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryPhoto

class PhotoDayViewModel : ViewModel() {

    fun createPushPhoto(context: Context, dateCalendar: String, curFile: Uri?) {
        BaseRepositoryPhoto.baseRepositoryUploadImageToStorage(context, dateCalendar, curFile)
    }

}