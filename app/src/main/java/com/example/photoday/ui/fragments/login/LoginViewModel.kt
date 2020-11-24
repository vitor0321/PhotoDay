package com.example.photoday.ui.fragments.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.LoginRepositoryShared

class LoginViewModel(private val repository: LoginRepositoryShared) : ViewModel() {

    fun login() = repository.login()

    fun loginIn(): Boolean = repository.loginIn()

    fun logout() = repository.logout()

    fun noIsLogin(): Boolean = !loginIn()

    fun navFragmentLogin(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToOneFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentRegister(navFragment: NavController){
        /*navegando entre fragment usando o Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToRegister()
        navFragment.navigate(direction)
    }
}