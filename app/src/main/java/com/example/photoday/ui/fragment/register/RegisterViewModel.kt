package com.example.photoday.ui.fragment.register

import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryUser

class RegisterViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    fun signUpUser(registerUser: AppCompatEditText, registerUserPassword: AppCompatEditText) =
        repository.baseRepositoryCreateUserWithEmailAndPassword(
            registerUser,
            registerUserPassword,
            navFragment,
        )
}


