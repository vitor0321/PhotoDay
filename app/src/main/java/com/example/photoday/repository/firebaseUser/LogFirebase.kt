package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.navigation.Navigation
import com.example.photoday.repository.firebaseUser.user.ResourceUser
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object LogFirebase {
    private var auth = FirebaseAuth.getInstance()

    fun logoutFirebase(context: Context): LiveData<ResourceUser<Void>> {
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
                liveData.value = ResourceUser( error = e.message)
            }
        }
        return liveData
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        callback: (login: Int) -> Unit,
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
                                callback.invoke(FIRST_LOGIN)
                            }
                            else -> {
                                liveData.value = ResourceUser(message = R.string.auth_failed)
                            }
                        }
                    }
            } catch (e: Exception) {
                liveData.value = ResourceUser(error = e.message)
            }
        }
        return liveData
    }

    fun createUserWithEmailAndPassword(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
    ): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
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
                                        Navigation.navFragmentRegisterToLogin(controlNavigation)
                                    }
                                liveData.value = ResourceUser(
                                    message = R.string.check_your_email_and_confirm
                                )
                            }
                            !task.isSuccessful -> {
                                Navigation.navFragmentRegisterToLogin(controlNavigation)
                                liveData.value = ResourceUser(
                                    message = R.string.email_already_exists
                                )
                            }
                            else -> {
                                liveData.value = ResourceUser(
                                    message = R.string.authentication_failed_try_again
                                )
                            }
                        }
                    }
            } catch (e: Exception) {
                liveData.value = ResourceUser(error = e.message)
            }
        }
        return liveData
    }
}