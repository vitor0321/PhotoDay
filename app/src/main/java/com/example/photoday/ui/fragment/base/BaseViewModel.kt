package com.example.photoday.ui.fragment.base

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

    fun stateFragmentBottom(components: Components) {
        mutableStatusData.postValue(components)
    }
    private val mutableStatusData = MutableLiveData<Components>()
    val componentsMutable: MutableLiveData<Components> = mutableStatusData

}
