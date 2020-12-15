package com.example.photoday.ui.fragment.register

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FALSE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.fragment.base.BaseViewModel

class RegisterViewModel : ViewModel() {

    fun navFragment(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction =
            RegisterDirections.actionRegisterToLoginFragment()
        navFragment.navigate(direction)
    }

    fun sentStatusToBase(viewModelBase: BaseViewModel) {
        val components = Components(FALSE, FALSE)
        viewModelBase.stateFragment(components)
    }
}