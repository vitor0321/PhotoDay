package com.example.photoday.ui.fragment.login

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class LoginViewModelFactory(
    private val controlNavigation: NavController,
    private val context: Context?,
    private val requireActivity: FragmentActivity
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(controlNavigation) as T
    }
}