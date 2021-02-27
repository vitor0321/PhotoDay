package com.example.photoday.ui.fragment.gallery

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.repository.BaseRepositoryPhoto.baseRepositoryListFileDownload

class GalleryViewModel : ViewModel() {

    fun createPullPhotos(binding: FragmentGalleryBinding?, context: Context) {
        baseRepositoryListFileDownload(binding, context, null)
    }

}