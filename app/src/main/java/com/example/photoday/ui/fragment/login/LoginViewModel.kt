package com.example.photoday.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FALSE
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface

class LoginViewModel(private val repository: LoginRepositoryShared) : ViewModel() {

    fun login() = repository.login()

    fun loginIn(): Boolean = repository.loginIn()

    fun logout() = repository.logout()

    fun noIsLogin(): Boolean = !loginIn()

    fun navFragmentLogin(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToSplashLogin()
        navFragment.navigate(direction)
    }

    fun navFragmentRegister(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToRegister()
        navFragment.navigate(direction)
    }

    fun stateAppBarNavigation(sendDataToActivityInterface: SendDataToActivityInterface) {
        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        val components = Components(FALSE, FALSE)
        sendDataToActivityInterface.sendStateComponents(components)
    }
}