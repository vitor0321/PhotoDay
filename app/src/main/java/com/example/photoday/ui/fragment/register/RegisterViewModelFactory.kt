package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class RegisterViewModelFactory(
    private val context: Context?,
    private val controlNavigation: NavController
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(context, controlNavigation) as T
    }
}