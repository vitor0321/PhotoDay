package com.example.photoday.repository.firebase

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.EditText
import com.example.photoday.R
import com.example.photoday.constants.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

object ChangeUserFirebase {

    private var auth = FirebaseAuth.getInstance()

    fun changeNameUser(context: Context, newName: String) {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build()
            user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Utils.toast(context, R.string.name_change_successful)
                        }
                    }
        } catch (e: Exception) {
            e.message?.let { Utils.toast(context, it.toInt()) }
        }
    }

    fun changeImageUser(context: Context, image: Uri) {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(image)
                    .build()
            user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Utils.toast(context, R.string.image_change_successful)
                        }
                    }
        } catch (e: Exception) {
            e.message?.let { Utils.toast(context, it.toInt()) }
        }
    }

    fun forgotPassword(userEmail: EditText, context: Context) {
        try {
            when {
                userEmail.text.toString().isEmpty() -> {
                    Utils.toast(context, R.string.please_enter_email)
                }
                !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                    Utils.toast(context, R.string.please_enter_valid_email)
                }
                else -> {
                    auth.sendPasswordResetEmail(userEmail.text.toString())
                            .addOnCompleteListener { task ->
                                when {
                                    task.isSuccessful -> {
                                        Utils.toast(context, R.string.email_sent)
                                    }
                                    else -> {
                                        Utils.toast(context, R.string.unregistered_email)
                                    }
                                }
                            }
                }
            }
        } catch (e: Exception) {
            e.message?.let { Utils.toast(context, it.toInt()) }
        }
    }
}