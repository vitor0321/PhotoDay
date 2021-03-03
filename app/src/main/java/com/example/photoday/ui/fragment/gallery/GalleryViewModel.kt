package com.example.photoday.ui.fragment.gallery

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.repository.BaseRepositoryPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<List<ItemPhoto>>(emptyList())
    val uiStateFlow: StateFlow<List<ItemPhoto>> get() = _uiStateFlow

    fun createPullPhotos(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.baseRepositoryListFileDownload(context) { imagesList ->
                _uiStateFlow.value = imagesList
            }
        }
    }
}