package com.example.photoday.ui.fragment.configuration

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface

class ConfigurationViewModel : ViewModel() {

    fun stateAppBarNavigation(sendDataToActivityInterface: SendDataToActivityInterface) {
        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        val components = Components(TRUE, FALSE)
        sendDataToActivityInterface.sendStateComponents(components)
    }

    fun navFragmentLogin(navFragment: NavController) {
        val direction =
            ConfigurationFragmentDirections.actionConfigurationFragmentToLoginFragment()
        navFragment.navigate(direction)
    }

    fun navTimeline(navFragment: NavController){
        val direction =
            ConfigurationFragmentDirections.actionConfigurationFragmentToTimelineFragment()
        navFragment.navigate(direction)
    }
}