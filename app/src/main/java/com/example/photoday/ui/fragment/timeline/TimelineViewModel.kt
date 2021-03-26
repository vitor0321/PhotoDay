package com.example.photoday.ui.fragment.timeline

import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.databinding.data.ComponentsData
import com.example.photoday.ui.stateBarNavigation.Components

class TimelineViewModel(
    private val repository: BaseRepositoryPhoto,
) : ViewModel() {

    fun createPullPhotos() = repository.baseRepositoryListFileDownload()

}