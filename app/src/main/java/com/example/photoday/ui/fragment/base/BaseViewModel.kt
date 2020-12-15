package com.example.photoday.ui.fragment.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.fragment.timeline.TimelineFragmentDirections

class BaseViewModel : ViewModel() {

    fun navFragmentLogin(navFragment: NavController) {
        val direction =
            TimelineFragmentDirections.actionTimelineFragmentToConfigurationFragment()
        navFragment.navigate(direction)
    }

    private var statusLiveDataAppBar = MutableLiveData<Components>()
    val status: LiveData<Components> = statusLiveDataAppBar

    fun stateFragment(components: Components){
        statusLiveDataAppBar.value?.bottomNavigation = components.bottomNavigation
        statusLiveDataAppBar.value?.appBar = components.appBar
    }

    val statuss: MutableLiveData<Components> by lazy {
        MutableLiveData<Components>()
    }
}
