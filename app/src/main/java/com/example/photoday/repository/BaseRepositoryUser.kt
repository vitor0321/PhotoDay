package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.constants.Utils.toast
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.LogFirebase
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BaseRepositoryUser(
    private val repositoryChange: ChangeUserFirebase = ChangeUserFirebase,
    private val repositoryCheck: CheckUserFirebase = CheckUserFirebase,
    private val repositoryLog: LogFirebase = LogFirebase,
) {

    suspend fun baseRepositoryChangeNameUser(context: Context, name: String) {
        try {
            repositoryChange.changeNameUser(context, name)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun baseRepositoryForgotPassword(userEmail: EditText, context: Context) {
        try {
            repositoryChange.forgotPassword(userEmail, context)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
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
                repositoryCheck.updateUI(controlNavigation, startLog, context)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { message -> toast(context, message) }
                }
            }
        }
    }

    suspend fun baseRepositoryGetCurrentUserFirebase(
        context: Context,
        callback: (userFirebase: UserFirebase) -> Unit,
    ) {
        try {
            repositoryCheck.getCurrentUserFirebase(context) { userFirebase ->
                callback.invoke(userFirebase)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun baseRepositoryChangeImageUser(context: Context, image: Uri) {
        try {
            repositoryChange.changeImageUser(context, image)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun baseRepositoryLogoutFirebase(context: Context) {
        try {
            repositoryLog.logoutFirebase(context)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun baseRepositoryFirebaseAuthWithGoogle(
        idToken: String,
        controlNavigation: NavController,
        context: Context,
    ) {
        try {
            repositoryLog.firebaseAuthWithGoogle(idToken, context,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation, login, context)
                })
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
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
            repositoryLog.createUserWithEmailAndPassword(context,
                registerUser,
                registerUserPassword,
                controlNavigation)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
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
            repositoryLog.signInWithEmailAndPassword(
                loginUserId,
                loginPassword,
                requireActivity,
                context,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation, login, context)
                }
            )
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }
}
