package com.example.photoday.ui.fragment.fullScreenPhoto

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.navigation.Navigation

class FullScreenPhotoViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryPhoto,
    private val date: String
) : ViewModel() {

    fun deleteNote() = repository.baseRepositoryDeleteImage(date)

    fun navigation() {
        Navigation.navFragmentFullScreenPhotoToTimeline(navFragment)
    }
}