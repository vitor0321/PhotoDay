package com.example.fragmenttest.fragment.register

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class RegisterViewModel: ViewModel() {

    fun navFragment(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction =
            RegisterDirections.actionRegisterToLoginFragment()
        navFragment.navigate(direction)
    }
}