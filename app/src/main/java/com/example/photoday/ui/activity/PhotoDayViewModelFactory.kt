package com.example.photoday.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoday.repository.BaseRepositoryPhoto

class PhotoDayViewModelFactory(private val repository: BaseRepositoryPhoto): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotoDayViewModel(repository) as T
    }
}