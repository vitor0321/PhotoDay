package com.example.photoday.repository.firebaseUser

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.EditText
import com.example.photoday.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

object ChangeUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    fun changeNameUser(
        newName: String,
        context: Context,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callbackMessage.invoke(context.getString(R.string.name_change_successful))
                    }
                }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun changeImageUser(
        image: Uri,
        context: Context,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(image)
                .build()
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callbackMessage.invoke(context.getString(R.string.image_change_successful))
                    }
                }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun forgotPassword(
        userEmail: EditText,
        context: Context,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            when {
                userEmail.text.toString().isEmpty() -> {
                    callbackMessage.invoke(context.getString(R.string.please_enter_email))
                }
                !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                    callbackMessage.invoke(context.getString(R.string.please_enter_valid_email))
                }
                else -> {
                    auth.sendPasswordResetEmail(userEmail.text.toString())
                        .addOnCompleteListener { task ->
                            when {
                                task.isSuccessful -> {
                                    callbackMessage.invoke(context.getString(R.string.email_sent))
                                }
                                else -> {
                                    callbackMessage.invoke(context.getString(R.string.unregistered_email))
                                }
                            }
                        }
                }
            }
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }
}