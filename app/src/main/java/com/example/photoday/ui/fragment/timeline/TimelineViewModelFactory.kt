package com.example.photoday.ui.fragment.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoday.repository.BaseRepositoryPhoto

class TimelineViewModelFactory(private val repository: BaseRepositoryPhoto) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimelineViewModel(repository) as T
    }
}