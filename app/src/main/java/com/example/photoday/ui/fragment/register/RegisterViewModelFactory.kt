package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryUser

class RegisterViewModelFactory(
    private val controlNavigation: NavController,
    private val repository: BaseRepositoryUser
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(controlNavigation, repository) as T
    }
}