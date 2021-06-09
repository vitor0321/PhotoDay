package com.example.photoday.ui.fragment.timeline

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.model.item.ItemPhoto
import com.example.photoday.ui.navigation.Navigation

class TimelineViewModel(
    private val repository: BaseRepositoryPhoto,
    private val navFragment: NavController
) : ViewModel() {

    fun createPullPhotos()=repository.baseRepositoryListFileDownload()

    fun navFragment(itemPhoto: ItemPhoto) {
        Navigation.navFragmentTimelineToFullScreenPhoto(
            navFragment,
            itemPhoto
        )
    }
}
