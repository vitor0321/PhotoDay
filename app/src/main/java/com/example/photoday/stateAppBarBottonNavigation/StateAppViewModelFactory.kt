package com.example.photoday.stateAppBarBottonNavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StateAppViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StateAppViewModel() as T
    }
}