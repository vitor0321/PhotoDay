package com.example.photoday.ui.fragment.gallery

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.repository.BaseRepositoryPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<List<ItemPhoto>>(emptyList())
    val uiStateFlow: StateFlow<List<ItemPhoto>> get() = _uiStateFlow

    private val _uiStateFlowError = MutableStateFlow("")
    val uiStateFlowError: StateFlow<String> get() = _uiStateFlowError

    fun createPullPhotos() {
        viewModelScope.launch {
            repository.baseRepositoryListFileDownload(
                callback = { imagesList: List<ItemPhoto> -> _uiStateFlow.value = imagesList },
                callbackMessage = { messageError: String -> _uiStateFlowError.value = messageError }
            )
        }
    }
}