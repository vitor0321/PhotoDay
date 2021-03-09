package com.example.photoday.ui.fragment.timeline

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.repository.BaseRepositoryPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimelineViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<List<ItemPhoto>>(emptyList())
    val uiStateFlow: StateFlow<List<ItemPhoto>> get() = _uiStateFlow

    private val _uiStateFlowError = MutableStateFlow("")
    val uiStateFlowError: StateFlow<String> get() = _uiStateFlowError

    fun createPullPhotos(context: Context) {
        viewModelScope.launch {
            repository.baseRepositoryListFileDownload(context,
                callback = { imagesList: List<ItemPhoto> -> _uiStateFlow.value = imagesList },
                callbackError = { messageError: String -> _uiStateFlowError.value = messageError }
            )
        }
    }
}