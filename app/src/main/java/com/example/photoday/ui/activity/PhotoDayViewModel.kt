package com.example.photoday.ui.activity

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.repository.BaseRepositoryPhoto
import kotlinx.coroutines.launch

class PhotoDayViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    fun createPushPhoto(context: Context, dateCalendar: String, curFile: Uri?) {
        viewModelScope.launch {
            repository.baseRepositoryUploadImageToStorage(context, dateCalendar, curFile)
        }
    }
}