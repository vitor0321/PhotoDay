package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class ConfigurationViewModelFactory(
    private val context: Context?,
    private val layoutInflater: LayoutInflater
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConfigurationViewModel( context, layoutInflater) as T
    }
}