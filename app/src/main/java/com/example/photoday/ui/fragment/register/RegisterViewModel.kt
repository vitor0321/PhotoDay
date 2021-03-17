package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel(
    private val controlNavigation: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    fun signUpUser(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        context: Context,
    ) = repository.baseRepositoryCreateUserWithEmailAndPassword(
        registerUser,
        registerUserPassword,
        controlNavigation,
        context
    )
}


