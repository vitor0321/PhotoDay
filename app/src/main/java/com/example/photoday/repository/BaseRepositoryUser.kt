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

    fun baseRepositoryChangeImageUser(image: Uri) =
        repositoryChange.changeImageUser(image)

    fun baseRepositoryGetCurrentUserFirebase() = repositoryChange.getCurrentUserFirebase()

    fun baseRepositoryChangeNameUser(name: EditText) =
        repositoryChange.changeNameUser(name)

    fun baseRepositoryForgotPassword(userEmail: EditText) =
        repositoryChange.forgotPassword(userEmail)

    fun baseRepositoryUpdateUI(controlNavigation: NavController, startLog: Int) =
        repositoryCheck.updateUI(controlNavigation, startLog)

    fun baseRepositoryLogoutFirebase(context: Context) = repositoryLog.logoutFirebase(context)

    fun baseRepositoryCreateUserWithEmailAndPassword(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        controlNavigation: NavController,
    ) = repositoryLog.createUserWithEmailAndPassword(
        registerUser,
        registerUserPassword,
        controlNavigation,
    )

    fun baseRepositoryFirebaseAuthWithGoogle(
        idToken: String,
        controlNavigation: NavController,
    ): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        try {
            val returnBD = repositoryLog.firebaseAuthWithGoogle(idToken,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation, login)
                })
            liveData.value = ResourceUser(message = returnBD.value?.message)
        } catch (e: Exception) {
            liveData.value = ResourceUser(error =  e.message)
        }
        return liveData
    }


    fun baseRepositorySignInWithEmailAndPassword(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        controlNavigation: NavController,
    ): LiveData<ResourceUser<UserFirebase>> {
        val liveData = MutableLiveData<ResourceUser<UserFirebase>>()
        try {
            signInWithEmailAndPassword(
                loginUserId,
                loginPassword,
                requireActivity,
                callback = { login: Int ->
                    baseRepositoryUpdateUI(controlNavigation, login)
                },
                callbackError = { error ->
                    liveData.value = ResourceUser(message = error)
                },
                callbackMessage = { message ->
                    liveData.value = ResourceUser(error = message)
                }
            )
        } catch (e: Exception) {
            liveData.value = ResourceUser(error = e.message)
        }
        return liveData
    }

    private fun signInWithEmailAndPassword(
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
