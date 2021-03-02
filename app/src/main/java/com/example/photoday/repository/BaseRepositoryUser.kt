package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.constants.Utils
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase.changeImageUser
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase.changeNameUser
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase.forgotPassword
import com.example.photoday.repository.firebaseUser.CheckUserFirebase.getCurrentUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase.updateUI
import com.example.photoday.repository.firebaseUser.LogFirebase.createUserWithEmailAndPassword
import com.example.photoday.repository.firebaseUser.LogFirebase.firebaseAuthWithGoogle
import com.example.photoday.repository.firebaseUser.LogFirebase.logoutFirebase
import com.example.photoday.repository.firebaseUser.LogFirebase.signInWithEmailAndPassword
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object BaseRepositoryUser {
    fun baseRepositoryChangeNameUser(context: Context, name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                changeNameUser(context, name)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryChangeImageUser(context: Context, image: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                changeImageUser(context, image)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryForgotPassword(userEmail: EditText, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                forgotPassword(userEmail, context)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryUpdateUI(
        controlNavigation: NavController,
        startLog: Int,
        context: Context
    ) {
        try {
            updateUI(controlNavigation, startLog, context)
        } catch (e: Exception) {
            e.message?.let { Utils.toast(context, it.toInt()) }
        }
    }

    fun baseRepositoryGetCurrentUserFirebase(context: Context, callback: (userFirebase: UserFirebase) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getCurrentUserFirebase(context) { userFirebase ->
                    callback.invoke(userFirebase)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryLogoutFirebase(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                logoutFirebase(context)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryFirebaseAuthWithGoogle(
            idToken: String,
            controlNavigation: NavController,
            context: Context
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                firebaseAuthWithGoogle(idToken, controlNavigation, context)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryCreateUserWithEmailAndPassword(
            context: Context,
            registerUser: AppCompatEditText,
            registerUserPassword: AppCompatEditText,
            controlNavigation: NavController
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                createUserWithEmailAndPassword(context, registerUser, registerUserPassword, controlNavigation)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositorySignInWithEmailAndPassword(
            loginUserId: AppCompatEditText,
            loginPassword: AppCompatEditText,
            requireActivity: FragmentActivity,
            controlNavigation: NavController,
            context: Context
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                signInWithEmailAndPassword(
                        loginUserId,
                        loginPassword,
                        requireActivity,
                        controlNavigation,
                        context
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }
}