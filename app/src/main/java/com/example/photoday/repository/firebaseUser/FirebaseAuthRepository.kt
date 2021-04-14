package com.example.photoday.repository.firebaseUser

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
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
    private val auth: FirebaseAuth
) {

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
                liveData.value = ResourceUser(error = e.message)
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

    fun createUserWithEmailAndPassword(userLogin: UserLogin): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                /*Create New User */
                val task = auth.createUserWithEmailAndPassword(userLogin.email, userLogin.password)
                task.addOnCompleteListener { taskComplete ->
                    val user = auth.currentUser
                    when {
                        taskComplete.isSuccessful -> {
                            user!!.sendEmailVerification()
                                .addOnCompleteListener {
                                    liveData.value = ResourceUser(
                                        message = R.string.check_your_email_and_confirm,
                                        navigation = true
                                    )
                                }
                        }
                    }
                }
                task.addOnFailureListener { exception ->
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

    fun signInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        callback: (login: Int) -> Unit,
        callbackError: (error: Int) -> Unit,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            /*checking if the user exists*/
            auth.signInWithEmailAndPassword(
                loginUserId.text.toString(),
                loginPassword.text.toString()
            )
                .addOnCompleteListener(requireActivity) { task ->
                    when {
                        task.isSuccessful -> {
                            // Sign in success, update UI with the signed-in user's information
                            callback.invoke(FIRST_LOGIN)
                        }
                        else -> {
                            // If sign in fails, display a message to the user.
                            callbackError.invoke(R.string.login_failed)
                            callback.invoke(FIRST_LOGIN)
                        }
                    }
                }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }
}