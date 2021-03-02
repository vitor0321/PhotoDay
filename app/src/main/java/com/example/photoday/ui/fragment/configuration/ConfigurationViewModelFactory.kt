package com.example.photoday.ui.fragment.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoday.repository.BaseRepositoryUser

class ConfigurationViewModelFactory(private val repository: BaseRepositoryUser) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConfigurationViewModel(repository) as T
    }
}