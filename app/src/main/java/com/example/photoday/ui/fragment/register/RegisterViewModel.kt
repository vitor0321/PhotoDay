package com.example.photoday.ui.fragment.register

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class RegisterViewModel : ViewModel() {

    fun navFragment(navFragment: NavController) {
        /*Navigation between fragments Directions*/
        val direction =
            RegisterDirections.actionRegisterToLoginFragment()
        navFragment.navigate(direction)
    }
}