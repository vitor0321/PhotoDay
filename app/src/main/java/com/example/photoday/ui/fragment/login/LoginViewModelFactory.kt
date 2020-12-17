package com.example.photoday.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoday.repository.sharedPreferences.LoginRepositoryShared

class LoginViewModelFactory(
    private val repository: LoginRepositoryShared,
    private val logout: Logout) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            repository, logout) as T
    }
}