package com.example.photoday.ui.fragment.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConfigurationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConfigurationViewModel() as T
    }
}