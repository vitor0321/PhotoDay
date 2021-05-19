package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.constants.ERROR_LOGIN
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.GOODBYE
import com.example.photoday.ui.model.resource.ResourceUser
import com.example.photoday.ui.model.user.UserLogin
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
    fun logoutFirebase(): LiveData<ResourceUser<Void>> =
        MutableLiveData<ResourceUser<Void>>().apply {
            try {
                AuthUI.getInstance()
                    .signOut(context)
                    .addOnSuccessListener {
                        value = ResourceUser(
                            login = GOODBYE,
                            message = R.string.successfully_logged
                        )
                    }
            } catch (e: Exception) {
                value = ResourceUser(message = R.string.failure_api)
            }
        }

    fun firebaseAuthWithGoogle(
        idToken: String,
    ): LiveData<ResourceUser<Void>>  = MutableLiveData<ResourceUser<Void>>().apply {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    value = when {
                        task.isSuccessful -> {
                            // Sign in success, update UI with the signed-in user's information
                            ResourceUser(
                                login = FIRST_LOGIN,
                                message = R.string.login_success
                            )
                        }
                        else -> {
                            ResourceUser(message = R.string.auth_failed)
                        }
                    }
                }
        } catch (e: Exception) {
            value = ResourceUser(message = R.string.failure_api)
        }
    }

    fun signInWithEmailAndPassword(
        email: String, password: String
    ): LiveData<ResourceUser<Void>>  = MutableLiveData<ResourceUser<Void>>().apply {
        try {
            /*checking if the user exists*/
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    when {
                        task.isSuccessful -> {
                            // Sign in success, update UI with the signed-in user's information
                            value = when(auth.currentUser.isEmailVerified){
                                true->{
                                    ResourceUser(
                                        login = FIRST_LOGIN,
                                        message = R.string.login_success
                                    )
                                }
                                false ->{
                                    ResourceUser(login = ERROR_LOGIN)
                                }
                            }
                        }
                        else -> {
                            // If sign in fails, display a message to the user.
                            value = ResourceUser(
                                login = null,
                                message = R.string.login_failed
                            )
                        }
                    }
                }
        } catch (e: Exception) {
            value = ResourceUser(message = R.string.failure_api)
        }
    }

    fun createUserWithEmailAndPassword(userLogin: UserLogin): LiveData<ResourceUser<Void>> = MutableLiveData<ResourceUser<Void>>().apply {
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
                                        value = ResourceUser(
                                            message = R.string.check_your_email_and_confirm,
                                            navigation = true
                                        )
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    value = ResourceUser(message = errorRegister(exception))
                }
        } catch (e: IllegalArgumentException) {
            value = ResourceUser(message = R.string.authentication_failed_try_again)
        }
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