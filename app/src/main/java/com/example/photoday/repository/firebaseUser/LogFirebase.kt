package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.Utils.toast
import com.example.photoday.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LogFirebase {
    private var auth = FirebaseAuth.getInstance()

    suspend fun logoutFirebase(context: Context) {
        try {
            AuthUI.getInstance()
                .signOut(context)
                .addOnSuccessListener {
                    toast(context, R.string.successfully_logged.toString())
                }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun firebaseAuthWithGoogle(
        idToken: String,
        context: Context,
        callback: (login: Int) -> Unit
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
                            toast(context, R.string.auth_failed.toString())
                        }
                    }
                }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun createUserWithEmailAndPassword(
        context: Context,
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
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
                                    toast(context, R.string.check_your_email_and_confirm.toString())
                                }
                                !task.isSuccessful -> {
                                    Navigation.navFragmentRegisterToLogin(controlNavigation)
                                    toast(context, R.string.email_already_exists.toString())
                                }
                                else -> {
                                    toast(context,
                                        R.string.authentication_failed_try_again.toString())
                                }
                            }
                        }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun signInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        context: Context,
        callback: (login: Int) -> Unit
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
                            toast(context, R.string.login_failed.toString())
                            callback.invoke(FIRST_LOGIN)
                        }
                    }
                }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }
}