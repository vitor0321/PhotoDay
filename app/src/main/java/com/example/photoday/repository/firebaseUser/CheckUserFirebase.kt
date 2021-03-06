package com.example.photoday.repository.firebaseUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.TRUE
import com.example.photoday.ui.model.resource.ResourceUser
import com.google.firebase.auth.FirebaseAuth

class CheckUserFirebase(
    private val auth: FirebaseAuth
) {

    fun updateUI(startLog: Int): LiveData<ResourceUser<Void>> =
        MutableLiveData<ResourceUser<Void>>().apply {
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
                                        value = ResourceUser(
                                            login = ON_START,
                                            message = TRUE
                                        )
                                    }
                                    FIRST_LOGIN -> {
                                        value = ResourceUser(
                                            login = FIRST_LOGIN,
                                            message = TRUE
                                        )
                                    }
                                }
                            }
                            else -> {
                                value = ResourceUser(message = FALSE)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                value = ResourceUser(message = null)
            }
        }
}