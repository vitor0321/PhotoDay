package com.example.photoday.repository.firebaseUser

import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.model.resource.ResourceUser
import com.example.photoday.model.user.UserFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ChangeUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    private val mediator = MediatorLiveData<ResourceUser<UserFirebase?>>()
    fun changeImageUser(image: Uri): LiveData<ResourceUser<UserFirebase?>> {
        CoroutineScope(Dispatchers.Main).launch {
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
                                message = R.string.image_change_successful
                            )
                        }
                    }
            } catch (e: Exception) {
                val mediatorData = mediator.value
                when (mediator.value) {
                    null -> mediator.value =
                        ResourceUser(data = mediatorData?.data, error = e.message)
                }
            }
        }
        return mediator
    }

    private val liveDataUser = MutableLiveData<ResourceUser<UserFirebase>>()
    fun getCurrentUserFirebase(): LiveData<ResourceUser<UserFirebase>> {
        CoroutineScope(Dispatchers.Main).launch {
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
        }
        return liveDataUser
    }

    fun changeNameUser(newName: EditText): LiveData<ResourceUser<String?>> {
        val liveData = MutableLiveData<ResourceUser<String?>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                val name = newName.text.toString()
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            liveData.value = ResourceUser(
                                data = newName.text.toString(),
                                message = R.string.name_change_successful
                            )
                        }
                    }
            } catch (e: Exception) {
                liveData.value = ResourceUser(error = e.message)
            }
        }
        return liveData
    }

    fun forgotPassword(userEmail: EditText): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                when {

                    else -> {
                        auth.sendPasswordResetEmail(userEmail.text.toString())
                            .addOnCompleteListener { task ->
                                when {
                                    task.isSuccessful -> {
                                        liveData.value = ResourceUser(message = R.string.email_sent)
                                    }
                                    else -> {
                                        liveData.value =
                                            ResourceUser(message = R.string.unregistered_email)
                                    }
                                }
                            }
                    }
                }
            } catch (e: Exception) {
                liveData.value = ResourceUser(error = e.message)
            }
        }
        return liveData
    }
}