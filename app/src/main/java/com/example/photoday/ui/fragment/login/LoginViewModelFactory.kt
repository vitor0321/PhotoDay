package com.example.photoday.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryUser

class LoginViewModelFactory(
    private val controlNavigation: NavController,
    private val repository: BaseRepositoryUser
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(controlNavigation, repository) as T
    }
}