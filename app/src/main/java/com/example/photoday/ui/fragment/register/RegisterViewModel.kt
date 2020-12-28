package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.Uteis.showToast
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {

    fun navFragment(navFragment: NavController) {
        /*Navigation between fragments Directions*/
        val direction =
            RegisterDirections.actionRegisterToLoginFragment()
        navFragment.navigate(direction)
    }

    fun createUserWithEmailAndPassword(
        auth: FirebaseAuth,
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        context: Context?,
        controlNavigation: NavController
    ) {
        /*Create New User */
        auth.createUserWithEmailAndPassword(
            registerUser.text.toString(),
            registerUserPassword.text.toString()
        )
            .addOnCompleteListener { task ->
                val user = auth.currentUser
                when {
                    task.isSuccessful -> {
                        user!!.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                when {
                                    task.isSuccessful -> {
                                        showToast(
                                            context,
                                            context!!.getString(R.string.login_success)
                                        )
                                        navFragment(controlNavigation)
                                    }
                                }
                            }
                        context?.let {
                            showToast(
                                context,
                                context.getString(R.string.check_your_email_and_confirm)
                            )
                        }
                    }
                    !task.isSuccessful -> {
                        navFragment(controlNavigation)
                        showToast(
                            context,
                            context!!.getString(R.string.email_already_exists)
                        )
                    }
                    else -> {
                        context?.let {
                            showToast(
                                context,
                                context.getString(R.string.authentication_failed_try_again)
                            )
                        }
                    }
                }
            }
    }
}

