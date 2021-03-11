package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.ON_START
import com.example.photoday.navigation.Navigation.navFragmentLoginToSplashLogin
import com.example.photoday.navigation.Navigation.navFragmentLoginToTimeline
import com.example.photoday.repository.firebaseUser.user.ResourceUser
import com.google.firebase.auth.FirebaseAuth

object CheckUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    fun updateUI(
        controlNavigation: NavController,
        startLog: Int,
        context: Context,
    ): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
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
                                    liveData.value = ResourceUser(data = null,
                                        error = context.getString(R.string.login_is_success))
                                }
                                FIRST_LOGIN -> {
                                    navFragmentLoginToSplashLogin(controlNavigation)
                                    liveData.value = ResourceUser(data = null,
                                        error = context.getString(R.string.login_is_success))
                                }
                                }
                            }
                            else -> {
                                liveData.value = ResourceUser(data = null,
                                    error = context.getString(R.string.verify_your_email_address))
                            }
                    }
                }
            }
        } catch (e: Exception) {
            liveData.value = ResourceUser(data = null, error = e.message)
        }
        return liveData
    }
}