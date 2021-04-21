package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
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
                    .addOnFailureListener { exception ->
                        liveData.value = ResourceUser(message = errorFirebaseAuth(exception))
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
                    .addOnCompleteListener {
                        // Sign in success, update UI with the signed-in user's information
                        liveData.value = ResourceUser(
                            login = FIRST_LOGIN,
                            message = R.string.login_success
                        )
                    }
                    .addOnFailureListener { exception ->
                        liveData.value = ResourceUser(message = errorFirebaseAuth(exception))
                    }
            } catch (e: Exception) {
                liveData.value = ResourceUser(message = R.string.failure_api)
            }
        }
        return liveData
    }

    fun createUserWithEmailAndPassword(userLogin: UserLogin): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                /*Create New User */
                auth.createUserWithEmailAndPassword(userLogin.email, userLogin.password)
                    .addOnSuccessListener {task->
                        sendEmailConfirm { sendEmail ->
                            liveData.value = ResourceUser(
                                message = sendEmail.message,
                                navigation = sendEmail.navigation
                            )
                        }

                    }
                    .addOnFailureListener { exception ->
                        liveData.value = ResourceUser(message = errorFirebaseAuth(exception))
                    }
            } catch (e: IllegalArgumentException) {
                liveData.value = ResourceUser(message = R.string.authentication_failed_try_again)
            }
        }
        return liveData
    }

    fun signInWithEmailAndPassword(email: String, password: String): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        try {
            /*checking if the user exists*/
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) {
                    // Sign in success, update UI with the signed-in user's information
                    liveData.value = ResourceUser(
                        login = FIRST_LOGIN,
                        message = R.string.login_success
                    )
                }
                .addOnFailureListener { exception ->
                    // If sign in fails, display a message to the user.
                    val message: Int = errorFirebaseAuth(exception)
                    liveData.value = ResourceUser(
                        login = FIRST_LOGIN,
                        message = message
                    )
                }
        } catch (e: Exception) {
            liveData.value = ResourceUser(message = R.string.failure_api)
        }
        return liveData
    }

    private fun sendEmailConfirm(callback: (ResourceUser<Void>) -> Unit) {
        try {
            val user = auth.currentUser
            user?.let {
                user.sendEmailVerification()
                    .addOnSuccessListener {
                        callback.invoke(
                            ResourceUser(
                                message = R.string.check_your_email_and_confirm,
                                navigation = true
                            )
                        )
                    }
                    .addOnFailureListener { exception ->
                        callback.invoke(
                            ResourceUser(message = errorFirebaseAuth(exception))
                        )
                    }
            }
        } catch (e: Exception) {
            callback.invoke(
                ResourceUser(message = R.string.failure_api)
            )
        }
    }

    private fun errorFirebaseAuth(exception: Exception): Int {
        return when (exception) {
            is FirebaseAuthInvalidUserException,
            is FirebaseAuthInvalidCredentialsException -> R.string.email_or_password_wrong
            is FirebaseAuthWeakPasswordException -> R.string.password_needs_least_6_digits
            is FirebaseAuthUserCollisionException -> R.string.email_already_been
            else -> R.string.unknown_error
        }
    }
}