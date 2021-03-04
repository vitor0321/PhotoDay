package com.example.photoday.ui.activity

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDayViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    fun createPushPhoto(context: Context, dateCalendar: String, curFile: Uri?) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.baseRepositoryUploadImageToStorage(context, dateCalendar, curFile)
        }
    }
}