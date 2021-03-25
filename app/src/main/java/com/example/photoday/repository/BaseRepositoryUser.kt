package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.LogFirebase
import com.example.photoday.repository.firebaseUser.user.ResourceUser
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import com.google.firebase.auth.FirebaseAuth

class BaseRepositoryUser(
    private val repositoryChange: ChangeUserFirebase = ChangeUserFirebase,
    private val repositoryCheck: CheckUserFirebase = CheckUserFirebase,
    private val repositoryLog: LogFirebase = LogFirebase,
) {
    private var auth = FirebaseAuth.getInstance()

    fun baseRepositoryChangeImageUser(image: Uri, context: Context) =
        repositoryChange.changeImageUser(image, context)

    fun baseRepositoryGetCurrentUserFirebase() = repositoryChange.getCurrentUserFirebase()

    fun baseRepositoryChangeNameUser(name: EditText, context: Context) =
        repositoryChange.changeNameUser(name, context)

    fun baseRepositoryForgotPassword(userEmail: EditText, context: Context) =
        repositoryChange.forgotPassword(userEmail, context)

    fun baseRepositoryUpdateUI(controlNavigation: NavController, startLog: Int, context: Context) =
        repositoryCheck.updateUI(controlNavigation, startLog, context)

    fun baseRepositoryLogoutFirebase(context: Context) = repositoryLog.logoutFirebase(context)

    fun baseRepositoryCreateUserWithEmailAndPassword(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
        context: Context,
    ) = repositoryLog.createUserWithEmailAndPassword(
        registerUser,
        registerUserPassword,
        controlNavigation,
        context)

    fun baseRepositoryFirebaseAuthWithGoogle(
        idToken: String,
        controlNavigation: NavController,
        context: Context,
    ): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        try {
            repositoryLog.firebaseAuthWithGoogle(idToken,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation, login, context)
                },
                callbackMessage = { message ->
                    liveData.value = ResourceUser(data = null, error = message)
                })
        } catch (e: Exception) {
            liveData.value = ResourceUser(data = null, e.message)
        }
        return liveData
    }


    fun baseRepositorySignInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        controlNavigation: NavController,
        context: Context,
    ): LiveData<ResourceUser<UserFirebase>> {
        val liveData = MutableLiveData<ResourceUser<UserFirebase>>()
        try {
            signInWithEmailAndPassword(
                loginUserId,
                loginPassword,
                requireActivity,
                context,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation, login, context)
                },
                callbackMessage = { message ->
                    liveData.value = ResourceUser(data = null, error = message)
                }
            )
        } catch (e: Exception) {
            liveData.value = ResourceUser(data = null, error = e.message)
        }
        return liveData
    }

    private fun signInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        context: Context,
        callback: (login: Int) -> Unit,
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
                            callbackMessage.invoke(context.getString(R.string.login_failed))
                            callback.invoke(FIRST_LOGIN)
                        }
                    }
                }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }
}
