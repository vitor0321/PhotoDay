package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class ConfigurationViewModelFactory(
    private val context: Context?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConfigurationViewModel( context) as T
    }
}