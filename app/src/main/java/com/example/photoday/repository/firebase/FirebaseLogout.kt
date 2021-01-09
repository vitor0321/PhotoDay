package com.example.photoday.repository.firebase

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.Utils.toast
import com.example.photoday.ui.navigation.Navigation
import com.example.photoday.ui.navigation.Navigation.navFragmentLoginToSplashLogin
import com.example.photoday.ui.navigation.Navigation.navFragmentLoginToTimeline
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

object FirebaseLogout {

    var auth = FirebaseAuth.getInstance()

    fun logout(context: Context) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnSuccessListener {

                toast(context,R.string.successfully_logged)
            }
    }

    fun forgotPassword(context: Context,userEmail: EditText) {
        when {
            userEmail.text.toString().isEmpty() -> {
                toast(context, R.string.please_enter_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                toast(context,R.string.please_enter_valid_email)
            }
            else -> {
                auth.sendPasswordResetEmail(userEmail.text.toString())
                    .addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                toast(context,R.string.email_sent)
                            }
                            else -> {
                                toast(context,R.string.unregistered_email)
                            }
                        }
                    }
            }
        }
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        controlNavigation: NavController,
        context: Context
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user, controlNavigation, FIRST_LOGIN, context)
                    }
                    else -> {
                        toast(context, R.string.auth_failed)
                    }
                }
            }
    }

    fun createUserWithEmailAndPassword(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
        context: Context
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
                            .addOnCompleteListener {
                                toast(context, R.string.login_success)
                                Navigation.navFragmentRegisterToLogin(controlNavigation)
                            }
                        toast(context,R.string.check_your_email_and_confirm)
                    }
                    !task.isSuccessful -> {
                        Navigation.navFragmentRegisterToLogin(controlNavigation)
                        toast(context,R.string.email_already_exists)
                    }
                    else -> {
                        toast(context,R.string.authentication_failed_try_again)
                    }
                }
            }
    }

    fun signInWithEmailAndPassword(
        login_user_id: AppCompatEditText,
        login_password: AppCompatEditText,
        requireActivity: FragmentActivity,
        controlNavigation: NavController,
        context: Context
    ) {
        /*checking if the user exists*/
        auth.signInWithEmailAndPassword(
            login_user_id.text.toString(),
            login_password.text.toString()
        )
            .addOnCompleteListener(requireActivity) { task ->
                when {
                    task.isSuccessful -> {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user, controlNavigation, FIRST_LOGIN, context)
                    }
                    else -> {
                        // If sign in fails, display a message to the user.
                        toast(context, R.string.login_failed)
                        updateUI(null, controlNavigation, FIRST_LOGIN, context)
                    }
                }
            }
    }

    fun updateUI(
        currentUser: FirebaseUser?,
        controlNavigation: NavController,
        onStart: Int,
        context: Context
    ) {
        /*if the user is different from null, then he exists and can log in*/
        when {
            currentUser != null -> {
                when {
                    currentUser.isEmailVerified -> {
                        /*if you are already logged in go to Timeline,
                        if you are going to log in for the first time go to Splash*/
                        when (onStart) {
                            ON_START -> {
                                navFragmentLoginToTimeline(controlNavigation)
                            }
                            FIRST_LOGIN -> {
                                navFragmentLoginToSplashLogin(controlNavigation)
                            }
                        }
                    }
                    else -> {
                        toast(context,R.string.verify_your_email_address)
                    }
                }
            }
        }
    }
}