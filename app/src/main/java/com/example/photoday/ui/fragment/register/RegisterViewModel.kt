package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.firebase.FirebaseLog.createUserWithEmailAndPassword

class RegisterViewModel(
    private val context: Context?,
    private val controlNavigation: NavController
) : ViewModel() {

    fun signUpUser(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText
    ) {
        context?.let {
            createUserWithEmailAndPassword(
                registerUser,
                registerUserPassword,
                controlNavigation,
                context
            )
        }
    }
}


