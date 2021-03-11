package com.example.photoday.ui.fragment.timeline

import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryPhoto

class TimelineViewModel(private val repository: BaseRepositoryPhoto) : ViewModel() {

    fun createPullPhotos() = repository.baseRepositoryListFileDownload()
}