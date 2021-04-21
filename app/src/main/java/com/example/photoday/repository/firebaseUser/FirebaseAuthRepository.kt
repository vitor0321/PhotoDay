package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.constants.ERROR_LOGIN
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.model.resource.ResourceUser
import com.example.photoday.model.user.UserLogin
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseAuthRepository(
    private val auth: FirebaseAuth,
    private val activity: FragmentActivity,
    private val context: Context
) {

    fun logoutFirebase(): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                AuthUI.getInstance()
                    .signOut(context)
                    .addOnSuccessListener {
                        liveData.value = ResourceUser(
                            message = R.string.successfully_logged
                        )
                    }
            } catch (e: Exception) {
                liveData.value = ResourceUser(message = R.string.failure_api)
            }
        }
        return liveData
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
    ): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                // Sign in success, update UI with the signed-in user's information
                                liveData.value = ResourceUser(
                                    login = FIRST_LOGIN,
                                    message = R.string.login_success
                                )
                            }
                            else -> {
                                liveData.value = ResourceUser(message = R.string.auth_failed)
                            }
                        }
                    }
            } catch (e: Exception) {
                liveData.value = ResourceUser(message = R.string.failure_api)
            }
        }
        return liveData
    }

    fun signInWithEmailAndPassword(
        email: String, password: String
    ): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        try {
            /*checking if the user exists*/
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    when {
                        task.isSuccessful -> {
                            // Sign in success, update UI with the signed-in user's information
                            when(auth.currentUser.isEmailVerified){
                                true->{
                                    liveData.value = ResourceUser(
                                        login = FIRST_LOGIN,
                                        message = R.string.login_success
                                    )
                                }
                                false ->{
                                    liveData.value = ResourceUser(login = ERROR_LOGIN)
                                }
                            }
                        }
                        else -> {
                            // If sign in fails, display a message to the user.
                            liveData.value = ResourceUser(
                                login = null,
                                message = R.string.login_failed
                            )
                        }
                    }
                }
        } catch (e: Exception) {
            liveData.value = ResourceUser(message = R.string.failure_api)
        }
        return liveData
    }

    fun createUserWithEmailAndPassword(userLogin: UserLogin): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                /*Create New User */
                auth.createUserWithEmailAndPassword(userLogin.email, userLogin.password)
                    .addOnCompleteListener { task ->
                        val user = auth.currentUser
                        when {
                            task.isSuccessful -> {
                                user?.let {
                                    user.sendEmailVerification()
                                        .addOnCompleteListener {
                                            liveData.value = ResourceUser(
                                                message = R.string.check_your_email_and_confirm,
                                                navigation = true
                                            )
                                        }
                                }
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        liveData.value = ResourceUser(message = errorRegister(exception))
                    }
            } catch (e: IllegalArgumentException) {
                liveData.value = ResourceUser(message = R.string.authentication_failed_try_again)
            }
        }
        return liveData
    }

    private fun errorRegister(exception: Exception): Int {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> R.string.password_needs_least_6_digits
            is FirebaseAuthInvalidCredentialsException -> R.string.email_invalid
            is FirebaseAuthUserCollisionException -> R.string.email_already_been
            else -> R.string.unknown_error
        }
    }
}