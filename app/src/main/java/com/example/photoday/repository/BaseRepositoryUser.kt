package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.constants.Utils.toast
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
    suspend fun baseRepositoryChangeNameUser(context: Context, name: String) {
        try {
            changeNameUser(context, name)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositoryForgotPassword(userEmail: EditText, context: Context) {
        try {
            forgotPassword(userEmail, context)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    fun baseRepositoryUpdateUI(
        controlNavigation: NavController,
        startLog: Int,
        context: Context,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                updateUI(controlNavigation, startLog, context)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { message -> toast(context, message.toInt()) }
                }
            }
        }
    }

    suspend fun baseRepositoryGetCurrentUserFirebase(
        context: Context,
        callback: (userFirebase: UserFirebase) -> Unit,
    ) {
        try {
            getCurrentUserFirebase(context) { userFirebase ->
                callback.invoke(userFirebase)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositoryChangeImageUser(context: Context, image: Uri) {
        try {
            changeImageUser(context, image)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositoryLogoutFirebase(context: Context) {
        try {
            logoutFirebase(context)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositoryFirebaseAuthWithGoogle(
        idToken: String,
        controlNavigation: NavController,
        context: Context,
    ) {
        try {
            firebaseAuthWithGoogle(idToken, controlNavigation, context)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositoryCreateUserWithEmailAndPassword(
        context: Context,
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
    ) {
        try {
            createUserWithEmailAndPassword(context,
                registerUser,
                registerUserPassword,
                controlNavigation)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositorySignInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        controlNavigation: NavController,
        context: Context,
    ) {
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
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }
}
