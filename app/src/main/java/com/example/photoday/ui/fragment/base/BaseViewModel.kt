package com.example.photoday.ui.fragment.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.timeline.TimelineFragmentDirections

class BaseViewModel : ViewModel() {

    fun navFragmentLogin(
        navFragment: NavController,
    ) {
        val direction =
            TimelineFragmentDirections.actionGlobalConfigurationFragment()
        navFragment.navigate(direction)
    }
}