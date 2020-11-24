package com.example.photoday.ui.fragments.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TimelineViewModelFatory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimelineViewModel() as T
    }
}