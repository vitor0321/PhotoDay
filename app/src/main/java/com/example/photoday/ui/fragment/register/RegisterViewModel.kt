package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryUser.baseRepositoryCreateUserWithEmailAndPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val controlNavigation: NavController) : ViewModel() {

    fun signUpUser(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        context: Context?,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                baseRepositoryCreateUserWithEmailAndPassword(
                    context,
                    registerUser,
                    registerUserPassword,
                    controlNavigation
                )
            }
        }
    }
}


