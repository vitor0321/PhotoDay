package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.photoday.model.resource.ResourceUser
import com.example.photoday.model.user.UserFirebase
import com.example.photoday.model.user.UserLogin
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.FirebaseAuthRepository

class BaseRepositoryUser(
    private val repositoryChange: ChangeUserFirebase,
    private val repositoryCheck: CheckUserFirebase,
    private val repositoryFirebaseAuth: FirebaseAuthRepository,
) {

    fun baseRepositoryChangeImageUser(image: Uri) =
        repositoryChange.changeImageUser(image)

    fun baseRepositoryGetCurrentUserFirebase() =
        repositoryChange.getCurrentUserFirebase()

    fun baseRepositoryChangeNameUser(name: EditText) =
        repositoryChange.changeNameUser(name)

    fun baseRepositoryForgotPassword(userEmail: EditText) =
        repositoryChange.forgotPassword(userEmail)

    fun baseRepositoryUpdateUI(controlNavigation: NavController, startLog: Int) =
        repositoryCheck.updateUI(controlNavigation, startLog)

    fun baseRepositoryLogoutFirebase(context: Context) =
        repositoryFirebaseAuth.logoutFirebase(context)

    fun baseRepositoryCreateUserWithEmailAndPassword(userLogin: UserLogin): LiveData<ResourceUser<Void>> =
        repositoryFirebaseAuth.createUserWithEmailAndPassword(userLogin)

    fun baseRepositoryFirebaseAuthWithGoogle(
        idToken: String,
        controlNavigation: NavController,
    ): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        try {
            val returnBD = repositoryFirebaseAuth.firebaseAuthWithGoogle(idToken,
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
            repositoryFirebaseAuth.signInWithEmailAndPassword(
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


}
