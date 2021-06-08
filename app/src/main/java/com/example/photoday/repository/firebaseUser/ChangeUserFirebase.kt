package com.example.photoday.repository.firebaseUser

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.ui.model.resource.ResourceUser
import com.example.photoday.ui.model.user.UserFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ChangeUserFirebase(
    private val auth: FirebaseAuth
) {

    fun changeImageUser(image: Uri): LiveData<ResourceUser<UserFirebase?>> =
        MediatorLiveData<ResourceUser<UserFirebase?>>().apply {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(image)
                    .build()
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userPhoto = UserFirebase(image = image)
                            value = ResourceUser(
                                data = userPhoto,
                                message = TRUE
                            )
                        }
                    }
            } catch (e: Exception) {
                val mediatorData = value
                when (value) {
                    null -> value =
                        ResourceUser(data = mediatorData?.data, message = FALSE)
                }
            }
        }

    fun getCurrentUserFirebase(): LiveData<ResourceUser<UserFirebase>> =
        MutableLiveData<ResourceUser<UserFirebase>>().apply {
            try {
                val user = UserFirebase()
                val auth = auth.currentUser
                auth?.let {
                    user.name = auth.displayName
                    user.email = auth.email.toString()
                    user.image = auth.photoUrl
                }
                value = ResourceUser(data = user)
            } catch (e: Exception) {
                val liveDataKeep = value
                when (value) {
                    null -> value =
                        ResourceUser(data = liveDataKeep?.data, message = FALSE)
                }
            }
        }


    fun changeNameUser(newName: UserFirebase): LiveData<ResourceUser<UserFirebase?>> =
        MutableLiveData<ResourceUser<UserFirebase?>>().apply {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(newName.name)
                    .build()
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            value = ResourceUser(
                                data = newName,
                                message = TRUE
                            )
                        }
                    }
            } catch (e: Exception) {
                value = ResourceUser(message = FALSE)
            }
        }

    fun forgotPassword(email: String): LiveData<ResourceUser<Void>> =
        MutableLiveData<ResourceUser<Void>>().apply {
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        value = when {
                            task.isSuccessful -> {
                                ResourceUser(message = TRUE)
                            }
                            else -> {
                                ResourceUser(message = FALSE)
                            }
                        }
                    }
            } catch (e: Exception) {
                value = ResourceUser(message = null)
            }
        }
}