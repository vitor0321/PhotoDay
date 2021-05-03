package com.example.photoday.repository.firebaseUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.ON_START
import com.example.photoday.ui.model.resource.ResourceUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckUserFirebase(
    private val auth: FirebaseAuth
) {

    fun updateUI(startLog: Int): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
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
                                        liveData.value = ResourceUser(
                                            login = ON_START,
                                            message = R.string.login_is_success
                                        )
                                    }
                                    FIRST_LOGIN -> {
                                        liveData.value = ResourceUser(
                                            login = FIRST_LOGIN,
                                            message = R.string.login_is_success
                                        )
                                    }
                                }
                            }
                            else -> {
                                liveData.value =
                                    ResourceUser(message = R.string.verify_your_email_address)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                liveData.value = ResourceUser(message = R.string.failure_api)
            }
        }
        return liveData
    }
}