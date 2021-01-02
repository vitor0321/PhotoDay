package com.example.photoday.ui.fragment.register

import android.content.Context
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.repository.firebase.FirebaseLogout.createUserWithEmailAndPassword
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {

    fun signUpUser(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        registerConfirmPassword: AppCompatEditText,
        context: Context,
        controlNavigation: NavController
    ) {
        /*here you will authenticate your email and password*/
        when {
            registerUser.text.toString().isEmpty() -> {
                registerUser.error = context.getString(R.string.please_enter_email)
                registerUser.requestFocus()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(registerUser.text.toString()).matches() -> {
                registerUser.error = context.getString(R.string.please_enter_valid_email)
                registerUser.requestFocus()
                return
            }
            registerUserPassword.text.toString().isEmpty() -> {
                registerUserPassword.error = context.getString(R.string.please_enter_password)
                registerUserPassword.requestFocus()
                return
            }
            registerConfirmPassword.text.toString().isEmpty() -> {
                registerUserPassword.error =
                    context.getString(R.string.please_enter_password_confirm)
                registerUserPassword.requestFocus()
                return
            }
            registerConfirmPassword.text.toString() != registerUserPassword.text.toString() -> {
                registerUserPassword.error = context.getString(R.string.password_are_not_the_same)
                registerUserPassword.requestFocus()
                return
            }
        }

        createUserWithEmailAndPassword(
            registerUser,
            registerUserPassword,
            controlNavigation,
            context
        )
    }
}


