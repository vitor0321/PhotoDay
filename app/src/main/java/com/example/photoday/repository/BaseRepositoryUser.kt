package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.LogFirebase
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseRepositoryUser(
    private val repositoryChange: ChangeUserFirebase = ChangeUserFirebase,
    private val repositoryCheck: CheckUserFirebase = CheckUserFirebase,
    private val repositoryLog: LogFirebase = LogFirebase,
) {

    fun baseRepositoryChangeNameUser(
        name: String,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryChange.changeNameUser(name,
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositoryForgotPassword(
        userEmail: EditText,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryChange.forgotPassword(userEmail,
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositoryUpdateUI(
        controlNavigation: NavController,
        startLog: Int,
        callbackMessage: (message: String) -> Unit,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repositoryCheck.updateUI(controlNavigation,
                    startLog,
                    callbackMessage = { message -> callbackMessage.invoke(message) })
            } catch (e: Exception) {
                e.message?.let { message -> callbackMessage.invoke(message) }
            }
        }
    }

    fun baseRepositoryGetCurrentUserFirebase(
        callback: (userFirebase: UserFirebase) -> Unit,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryCheck.getCurrentUserFirebase(
                callback = { userFirebase ->
                    callback.invoke(userFirebase)
                },
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositoryChangeImageUser(
        image: Uri,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryChange.changeImageUser(image,
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositoryLogoutFirebase(
        context: Context,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryLog.logoutFirebase(context,
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositoryFirebaseAuthWithGoogle(
        idToken: String,
        controlNavigation: NavController,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryLog.firebaseAuthWithGoogle(idToken,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation,
                        login,
                        callbackMessage = { message -> callbackMessage.invoke(message) })
                },
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositoryCreateUserWithEmailAndPassword(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryLog.createUserWithEmailAndPassword(
                registerUser,
                registerUserPassword,
                controlNavigation,
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositorySignInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        controlNavigation: NavController,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryLog.signInWithEmailAndPassword(
                loginUserId,
                loginPassword,
                requireActivity,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation,
                        login,
                        callbackMessage = { message -> callbackMessage.invoke(message) })
                },
                callbackMessage = { message -> callbackMessage.invoke(message) }
            )
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }
}
