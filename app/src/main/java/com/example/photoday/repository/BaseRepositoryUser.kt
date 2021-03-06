package com.example.photoday.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.photoday.ui.model.resource.ResourceUser
import com.example.photoday.ui.model.user.UserLogin
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.FirebaseAuthRepository
import com.example.photoday.ui.model.user.UserFirebase

class BaseRepositoryUser(
    private val repositoryChange: ChangeUserFirebase,
    private val repositoryCheck: CheckUserFirebase,
    private val repositoryFirebaseAuth: FirebaseAuthRepository,
) {

    fun baseRepositoryChangeImageUser(image: Uri) =
        repositoryChange.changeImageUser(image)

    fun baseRepositoryGetCurrentUserFirebase() =
        repositoryChange.getCurrentUserFirebase()

    fun baseRepositoryChangeNameUser(newName: UserFirebase) =
        repositoryChange.changeNameUser(newName)

    fun baseRepositoryForgotPassword(email: String) =
        repositoryChange.forgotPassword(email)

    fun baseRepositoryUpdateUI(startLog: Int) =
        repositoryCheck.updateUI(startLog)

    fun baseRepositoryLogoutFirebase() =
        repositoryFirebaseAuth.logoutFirebase()

    fun baseRepositoryCreateUserWithEmailAndPassword(userLogin: UserLogin): LiveData<ResourceUser<Void>> =
        repositoryFirebaseAuth.createUserWithEmailAndPassword(userLogin)

    fun baseRepositoryFirebaseAuthWithGoogle(
        idToken: String,
    ) = repositoryFirebaseAuth.firebaseAuthWithGoogle(idToken)

    fun baseRepositorySignInWithEmailAndPassword(email: String, password: String) =
        repositoryFirebaseAuth.signInWithEmailAndPassword(email, password)
}
