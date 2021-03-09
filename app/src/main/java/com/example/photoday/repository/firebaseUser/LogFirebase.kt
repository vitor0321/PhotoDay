package com.example.photoday.repository.firebaseUser

import android.content.Context
import android.content.res.Resources.getSystem
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object LogFirebase {
    private var auth = FirebaseAuth.getInstance()

    fun logoutFirebase(
        context: Context,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            AuthUI.getInstance()
                .signOut(context)
                .addOnSuccessListener {
                    callbackMessage.invoke(context.getString(R.string.successfully_logged))
                }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        callback: (login: Int) -> Unit,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    when {
                        task.isSuccessful -> {
                            // Sign in success, update UI with the signed-in user's information
                            callback.invoke(FIRST_LOGIN)
                        }
                        else -> {
                            callbackMessage.invoke(getSystem().getString(R.string.auth_failed))
                        }
                    }
                }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun createUserWithEmailAndPassword(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
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
                                        .addOnCompleteListener {
                                            Navigation.navFragmentRegisterToLogin(controlNavigation)
                                        }
                                    callbackMessage.invoke(getSystem().getString(R.string.check_your_email_and_confirm))
                                }
                                !task.isSuccessful -> {
                                    Navigation.navFragmentRegisterToLogin(controlNavigation)
                                    callbackMessage.invoke(getSystem().getString(R.string.email_already_exists))
                                }
                                else -> {
                                    callbackMessage.invoke(getSystem().getString(R.string.authentication_failed_try_again))
                                }
                            }
                        }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun signInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        callback: (login: Int) -> Unit,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            /*checking if the user exists*/
            auth.signInWithEmailAndPassword(
                loginUserId.text.toString(),
                loginPassword.text.toString()
            )
                .addOnCompleteListener(requireActivity) { task ->
                    when {
                        task.isSuccessful -> {
                            // Sign in success, update UI with the signed-in user's information
                            callback.invoke(FIRST_LOGIN)
                        }
                        else -> {
                            // If sign in fails, display a message to the user.
                            callbackMessage.invoke(getSystem().getString(R.string.login_failed))
                            callback.invoke(FIRST_LOGIN)
                        }
                    }
                }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }
}