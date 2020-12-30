package com.example.photoday.ui.fragment.login

import android.content.Context
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRSTLOGIN
import com.example.photoday.constants.ONSTART
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.navigation.Navigation.navFragmentLoginToSplashLogin
import com.example.photoday.navigation.Navigation.navFragmentLoginToTimeline
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser

object Logout {
    fun logout(context: Context) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnSuccessListener {
                showToast(context, context.getString(R.string.successfully_logged))
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