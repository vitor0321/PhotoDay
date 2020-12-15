package com.example.photoday.ui.fragment.gallery

import androidx.lifecycle.ViewModel
import com.example.photoday.constants.TRUE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.fragment.base.BaseViewModel

class GalleryViewModel : ViewModel() {

    fun sentStatusToBase(viewModelBase: BaseViewModel) {
        val components = Components(TRUE, TRUE)
        viewModelBase.stateFragment(components)
    }
}