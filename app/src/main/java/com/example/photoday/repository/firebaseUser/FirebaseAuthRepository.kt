package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.constants.*
import com.example.photoday.ui.model.resource.ResourceUser
import com.example.photoday.ui.model.user.UserLogin
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
                        value = ResourceUser(login = GOODBYE)
                    }
            } catch (e: Exception) {
                value = ResourceUser(login = GOODBYE_FAILURE)
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
                        // Sign in success, update UI with the signed-in user's information
                        task.isSuccessful -> ResourceUser(login = FIRST_LOGIN, message = TRUE)
                        else -> ResourceUser(message = null)
                    }
                }
                .addOnFailureListener { value = ResourceUser(message = FALSE) }
        } catch (e: Exception) {
            value = ResourceUser(message = null)
        }
    }

    fun signInWithEmailAndPassword(
        email: String, password: String
    ): LiveData<ResourceUser<Void>>  = MutableLiveData<ResourceUser<Void>>().apply {
        try {
            /*checking if the user exists*/
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    // Sign in success, update UI with the signed-in user's information
                    task.addOnSuccessListener {
                        value = when (auth.currentUser.isEmailVerified) {
                            true -> ResourceUser(login = FIRST_LOGIN, message = TRUE)
                            false -> ResourceUser(login = ERROR_LOGIN)
                        }
                    }
                    task.addOnFailureListener {
                        // If sign in fails, display a message to the user.
                        value = ResourceUser(login = null, message = null)
                    }
                }
        } catch (e: Exception) {
            value = ResourceUser(message = null)
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
                                            message = TRUE,
                                            navigation = true
                                        )
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    value = ResourceUser(message = FALSE)
                }
        } catch (e: IllegalArgumentException) {
            value = ResourceUser(message = FALSE)
        }
    }
}