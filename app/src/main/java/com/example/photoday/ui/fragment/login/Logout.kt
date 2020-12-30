package com.example.photoday.ui.fragment.login

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRSTLOGIN
import com.example.photoday.constants.ONSTART
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.navigation.Navigation
import com.example.photoday.navigation.Navigation.navFragmentLoginToSplashLogin
import com.example.photoday.navigation.Navigation.navFragmentLoginToTimeline
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

object Logout {
    fun logout(context: Context) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnSuccessListener {
                showToast(context, context.getString(R.string.successfully_logged))
            }
    }

    fun forgotPassword(userEmail: EditText, context: Context?, auth: FirebaseAuth) {
        when {
            userEmail.text.toString().isEmpty() -> {
                showToast(context, context!!.getString(R.string.please_enter_email))
            }
            !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                showToast(context, context!!.getString(R.string.please_enter_valid_email))
            }
            else -> {
                auth.sendPasswordResetEmail(userEmail.text.toString())
                    .addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                showToast(context, context!!.getString(R.string.email_sent))
                            }
                            else -> {
                                showToast(context, context!!.getString(R.string.unregistered_email))
                            }
                        }
                    }
            }
        }
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        auth: FirebaseAuth,
        controlNavigation: NavController,
        context: Context?
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user, controlNavigation, context, FIRSTLOGIN)
                    }
                    else -> {
                        showToast(context, context!!.getString(R.string.auth_failed))
                    }
                }
            }
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
                            .addOnCompleteListener {
                                showToast(context, context!!.getString(R.string.login_success))
                                Navigation.navFragmentRegisterToLogin(controlNavigation)
                            }
                        showToast(
                            context, context!!.getString(R.string.check_your_email_and_confirm)
                        )
                    }
                    !task.isSuccessful -> {
                        Navigation.navFragmentRegisterToLogin(controlNavigation)
                        showToast(context, context!!.getString(R.string.email_already_exists))
                    }
                    else -> {
                        showToast(
                            context, context!!.getString(R.string.authentication_failed_try_again)
                        )
                    }
                }
            }
    }

    fun signInWithEmailAndPassword(
        auth: FirebaseAuth,
        login_user_id: AppCompatEditText,
        login_password: AppCompatEditText,
        requireActivity: FragmentActivity,
        context: Context?,
        controlNavigation: NavController
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
                        updateUI(user, controlNavigation, context, FIRSTLOGIN)
                    }
                    else -> {
                        // If sign in fails, display a message to the user.
                        showToast(context,context!!.getString(R.string.login_failed))
                        updateUI(null, controlNavigation, context, FIRSTLOGIN)
                    }
                }
            }
    }

    fun updateUI(
        currentUser: FirebaseUser?,
        controlNavigation: NavController,
        context: Context?,
        onStart: Int,
    ) {
        /*if the user is different from null, then he exists and can log in*/
        when {
            currentUser != null -> {
                when {
                    currentUser.isEmailVerified -> {
                        /*if you are already logged in go to Timeline,
                        if you are going to log in for the first time go to Splash*/
                        when (onStart) {
                            ONSTART -> {
                                navFragmentLoginToTimeline(controlNavigation)
                            }
                            FIRSTLOGIN -> {
                                navFragmentLoginToSplashLogin(controlNavigation)
                            }
                        }
                    }
                    else -> {
                        showToast(context, context!!.getString(R.string.verify_your_email_address))
                    }
                }
            }
        }
    }
}