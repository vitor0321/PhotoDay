package com.example.photoday.ui.fragment.gallery

import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryPhoto

class GalleryViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    fun createPullPhotos() = repository.baseRepositoryListFileDownload()
}