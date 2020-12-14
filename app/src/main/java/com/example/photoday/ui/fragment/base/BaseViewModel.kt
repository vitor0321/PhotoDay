package com.example.photoday.ui.fragment.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.TRUE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface
import com.example.photoday.ui.fragment.timeline.TimelineFragmentDirections

class BaseViewModel : ViewModel() {

    private lateinit var mutableStatusData : Components
    fun navFragmentLogin(navFragment: NavController) {
        val direction =
            TimelineFragmentDirections.actionTimelineFragmentToConfigurationFragment()
        navFragment.navigate(direction)
    }

    fun stateFragmentBottom(components: Components){
        mutableStatusData = components
    }

    fun stateAppBarNavigation(sendDataToActivityInterface: SendDataToActivityInterface) {
        val states = mutableStatusData
        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        sendDataToActivityInterface.sendStateComponents(states)
    }
}
