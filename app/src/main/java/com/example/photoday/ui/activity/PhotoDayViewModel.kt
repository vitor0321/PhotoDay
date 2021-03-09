package com.example.photoday.ui.activity

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.repository.BaseRepositoryPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoDayViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    private val _uiStateFlowMessage = MutableStateFlow("")
    val uiStateFlowMessage: StateFlow<String> get() = _uiStateFlowMessage

    fun createPushPhoto(dateCalendar: String, curFile: Uri?, context: Context) {
        viewModelScope.launch {
            repository.baseRepositoryUploadImageToStorage(
                dateCalendar,
                curFile,
                context,
                callbackMessage = { message -> _uiStateFlowMessage.value = message })
        }
    }
}