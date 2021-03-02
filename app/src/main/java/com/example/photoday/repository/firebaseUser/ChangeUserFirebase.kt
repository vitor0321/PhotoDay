package com.example.photoday.repository.firebaseUser

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.EditText
import com.example.photoday.R
import com.example.photoday.constants.Utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ChangeUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    fun changeNameUser(context: Context, newName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build()
                user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                toast(context, R.string.name_change_successful)
                            }
                        }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { toast(context, it.toInt()) }
                }
            }
        }
    }

    fun changeImageUser(context: Context, image: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                        .setPhotoUri(image)
                        .build()
                user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                toast(context, R.string.image_change_successful)
                            }
                        }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { toast(context, it.toInt()) }
                }
            }
        }
    }

    fun forgotPassword(userEmail: EditText, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when {
                    userEmail.text.toString().isEmpty() -> {
                        toast(context, R.string.please_enter_email)
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                        toast(context, R.string.please_enter_valid_email)
                    }
                    else -> {
                        auth.sendPasswordResetEmail(userEmail.text.toString())
                                .addOnCompleteListener { task ->
                                    when {
                                        task.isSuccessful -> {
                                            toast(context, R.string.email_sent)
                                        }
                                        else -> {
                                            toast(context, R.string.unregistered_email)
                                        }
                                    }
                                }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { toast(context, it.toInt()) }
                }
            }
        }
    }
}