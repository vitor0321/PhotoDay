package com.example.photoday.ui.fragment.gallery

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.ui.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryPhoto

class GalleryViewModel(
    private val repository: BaseRepositoryPhoto,
    private val navFragment: NavController
) : ViewModel() {

    fun createPullPhotos() = repository.baseRepositoryListFileDownload()

    fun navFragment(itemPhoto: String) {
        Navigation.navFragmentGalleryToFullScreen(navFragment, itemPhoto)
    }
}