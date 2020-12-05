package com.example.photoday.ui.fragment.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.NavGraphDirections
import com.example.photoday.ui.fragment.configuration.ConfigurationFragmentDirections
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.timeline.TimelineFragmentDirections

class BaseViewModel : ViewModel() {

    fun init(
        loginviewModel: LoginViewModel,
        controlNavigation: NavController
    ) {
        when {
            loginviewModel.noIsLogin() -> goToLogin(controlNavigation)
        }
    }

    fun goToLogin(controlNavigation: NavController) {
        val direction = ConfigurationFragmentDirections.actionConfigurationFragmentToLoginFragment()
        controlNavigation.navigate(direction)
    }

    fun navFragmentLogin(navFragment: NavController) {
        val direction =
            TimelineFragmentDirections.actionGlobalConfigurationFragment()
        navFragment.navigate(direction)
    }
}