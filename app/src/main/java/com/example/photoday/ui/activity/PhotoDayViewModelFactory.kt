package com.example.photoday.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PhotoDayViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotoDayViewModel() as T
    }
}