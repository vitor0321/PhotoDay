package com.example.photoday.ui.fragment.timeline

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.ui.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryPhoto

class TimelineViewModel(
    private val repository: BaseRepositoryPhoto,
    private val navFragment: NavController
) : ViewModel() {

    fun createPullPhotos() = repository.baseRepositoryListFileDownload()

    fun navFragment(itemPhoto: String) {
        Navigation.navFragmentTimelineToFullScreenPhoto(navFragment, itemPhoto)
    }
}