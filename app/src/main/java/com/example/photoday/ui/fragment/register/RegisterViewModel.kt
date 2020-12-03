package com.example.photoday.ui.fragment.register

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FALSE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface

class RegisterViewModel : ViewModel() {

    fun navFragment(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction =
            RegisterDirections.actionRegisterToLoginFragment()
        navFragment.navigate(direction)
    }

    fun stateAppBarNavigation(sendDataToActivityInterface: SendDataToActivityInterface) {
        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        val components = Components(FALSE, FALSE)
        sendDataToActivityInterface.sendStateComponents(components)
    }
}