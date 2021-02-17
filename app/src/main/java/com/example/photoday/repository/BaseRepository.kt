package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
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

object BaseRepository {
    fun baseRepositoryChangeNameUser(context: Context, name: String) {
        changeNameUser(context, name)
    }

    fun baseRepositoryChangeImageUser(context: Context, image: Uri) {
        changeImageUser(context, image)
    }

    fun baseRepositoryForgotPassword(userEmail: EditText, context: Context) {
        forgotPassword(userEmail, context)
    }

    fun baseRepositoryUpdateUI(
            controlNavigation: NavController,
            startLog: Int,
            context: Context
    ) {
        updateUI(controlNavigation, startLog, context)
    }

    fun baseRepositoryGetCurrentUserFirebase(): UserFirebase {
        return getCurrentUserFirebase()
    }

    fun baseRepositoryLogoutFirebase(context: Context) {
        logoutFirebase(context)
    }

    fun baseRepositoryFirebaseAuthWithGoogle(
            idToken: String,
            controlNavigation: NavController,
            context: Context
    ) {
        firebaseAuthWithGoogle(idToken, controlNavigation, context)
    }

    fun baseRepositoryCreateUserWithEmailAndPassword(
            context: Context,
            registerUser: AppCompatEditText,
            registerUserPassword: AppCompatEditText,
            controlNavigation: NavController
    ) {
        createUserWithEmailAndPassword(context, registerUser, registerUserPassword, controlNavigation)
    }

    fun baseRepositorySignInWithEmailAndPassword(
            loginUserId: AppCompatEditText,
            loginPassword: AppCompatEditText,
            requireActivity: FragmentActivity,
            controlNavigation: NavController,
            context: Context
    ){
        signInWithEmailAndPassword(
                loginUserId,
                loginPassword,
                requireActivity,
                controlNavigation,
                context
        )
    }
}