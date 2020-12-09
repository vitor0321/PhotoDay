package com.example.photoday.ui.fragment.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FALSE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface
import com.example.photoday.ui.fragment.timeline.TimelineFragmentDirections

class BaseViewModel : ViewModel() {

    fun goToLogin(controlNavigation: NavController) {
        val direction = TimelineFragmentDirections.actionTimelineFragmentToLoginFragment()
        controlNavigation.navigate(direction)
    }

    fun navFragmentLogin(navFragment: NavController) {
        val direction =
            TimelineFragmentDirections.actionGlobalConfigurationFragment()
        navFragment.navigate(direction)
    }
}