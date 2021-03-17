package com.example.photoday.repository.firebaseUser

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.repository.firebaseUser.user.ResourceUser
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

object ChangeUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    private val mediator = MediatorLiveData<ResourceUser<UserFirebase?>>()
    fun changeImageUser(image: Uri, context: Context): LiveData<ResourceUser<UserFirebase?>> {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(image)
                .build()
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userPhoto = UserFirebase(image = image)
                        mediator.value = ResourceUser(
                            data = userPhoto,
                            error = context.getString(R.string.image_change_successful))
                    }
                }
        } catch (e: Exception) {
            val mediator = mediator.value
            when (this.mediator.value) {
                null -> this.mediator.value = ResourceUser(data = mediator?.data, error = e.message)
            }
        }
        return mediator
    }

    private val liveDataUser = MutableLiveData<ResourceUser<UserFirebase>>()
    fun getCurrentUserFirebase(): LiveData<ResourceUser<UserFirebase>> {
        try {
            val user = UserFirebase()
            val auth = auth.currentUser
            auth?.let {
                user.name = auth.displayName
                user.email = auth.email.toString()
                user.image = auth.photoUrl
            }
            liveDataUser.value = ResourceUser(data = user)
        } catch (e: Exception) {
            val liveDataKeep = liveDataUser.value
            when (liveDataUser.value) {
                null -> liveDataUser.value =
                    ResourceUser(data = liveDataKeep?.data, error = e.message)
            }
        }
        return liveDataUser
    }

    fun changeNameUser(newName: String, context: Context): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        try {
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        liveData.value = ResourceUser(data = null,
                            error = context.getString(R.string.name_change_successful))
                    }
                }
        } catch (e: Exception) {
            liveData.value = ResourceUser(data = null, error = e.message)
        }
        return liveData
    }

    fun forgotPassword(userEmail: EditText, context: Context): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        try {
            when {
                userEmail.text.toString().isEmpty() -> {
                    liveData.value = ResourceUser(data = null,
                        error = context.getString(R.string.please_enter_email))
                }
                !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                    liveData.value = ResourceUser(data = null,
                        error = context.getString(R.string.please_enter_valid_email))
                }
                else -> {
                    auth.sendPasswordResetEmail(userEmail.text.toString())
                        .addOnCompleteListener { task ->
                            when {
                                task.isSuccessful -> {
                                    liveData.value = ResourceUser(data = null,
                                        error = context.getString(R.string.email_sent))
                                }
                                else -> {
                                    liveData.value = ResourceUser(data = null,
                                        error = context.getString(R.string.unregistered_email))
                                }
                            }
                        }
                }
            }
        } catch (e: Exception) {
            liveData.value = ResourceUser(data = null, error = e.message)
        }
        return liveData
    }
}