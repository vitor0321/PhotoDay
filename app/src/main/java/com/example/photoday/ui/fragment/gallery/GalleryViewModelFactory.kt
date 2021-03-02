package com.example.photoday.ui.fragment.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebasePhotos.FirebasePhoto

class GalleryViewModelFactory (private val repository : BaseRepositoryPhoto): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel( repository) as T
    }
}