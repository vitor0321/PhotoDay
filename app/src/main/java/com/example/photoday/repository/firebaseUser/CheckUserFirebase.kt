package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.Utils.toast
import com.example.photoday.navigation.Navigation.navFragmentLoginToSplashLogin
import com.example.photoday.navigation.Navigation.navFragmentLoginToTimeline
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CheckUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    suspend fun updateUI(
        controlNavigation: NavController,
        startLog: Int,
        context: Context,
    ) {
        try {
            val currentUser = auth.currentUser
            /*if the user is different from null, then he exists and can log in*/
            when {
                currentUser != null -> {
                    when {
                            currentUser.isEmailVerified -> {
                                /*if you are already logged in go to Timeline,
                        if you are going to log in for the first time go to Login*/
                                when (startLog) {
                                    ON_START -> {
                                        navFragmentLoginToTimeline(controlNavigation)
                                    }
                                    FIRST_LOGIN -> {
                                        navFragmentLoginToSplashLogin(controlNavigation)
                                        toast(context, R.string.login_is_success.toString())
                                    }
                                }
                            }
                            else -> {
                                toast(context, R.string.verify_your_email_address.toString())
                            }
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun getCurrentUserFirebase(
        context: Context,
        callback: (userFirebase: UserFirebase) -> Unit,
    ) {
        try {
            val user = UserFirebase()
            val auth = auth.currentUser
            auth?.let {
                user.name = auth.displayName
                user.email = auth.email.toString()
                user.image = auth.photoUrl
            }
            callback.invoke(user)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }
}