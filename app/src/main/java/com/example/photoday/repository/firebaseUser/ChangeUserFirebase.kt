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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ChangeUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    private val mediator = MediatorLiveData<ResourceUser<UserFirebase?>>()
    fun changeImageUser(image: Uri, context: Context): LiveData<ResourceUser<UserFirebase?>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(image)
                    .build()
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userPhoto = UserFirebase(
                                name = null,
                                email = null,
                                image = image
                            )
                            mediator.value = ResourceUser(
                                data = userPhoto,
                                error = context.getString(R.string.image_change_successful)
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

    fun changeNameUser(newName: EditText, context: Context): LiveData<ResourceUser<String?>> {
        val liveData = MutableLiveData<ResourceUser<String?>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when {
                    newName.text.toString().isEmpty() -> {
                        liveData.value = ResourceUser(
                            data = null,
                            error = context.getString(R.string.enter_valid_name)
                        )
                        newName.requestFocus()
                    }
                }
                val user = FirebaseAuth.getInstance().currentUser
                val name = newName.text.toString()
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            liveData.value = ResourceUser(
                                data = name,
                                error = context.getString(R.string.name_change_successful)
                            )
                        }
                    }
            } catch (e: Exception) {
                liveData.value = ResourceUser(data = null, error = e.message)
            }
        }
        return liveData
    }

    fun forgotPassword(userEmail: EditText, context: Context): LiveData<ResourceUser<Void>> {
        val liveData = MutableLiveData<ResourceUser<Void>>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                when {
                    userEmail.text.toString().isEmpty() -> {
                        liveData.value = ResourceUser(
                            data = null,
                            error = context.getString(R.string.please_enter_email)
                        )
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                        liveData.value = ResourceUser(
                            data = null,
                            error = context.getString(R.string.please_enter_valid_email)
                        )
                    }
                    else -> {
                        auth.sendPasswordResetEmail(userEmail.text.toString())
                            .addOnCompleteListener { task ->
                                when {
                                    task.isSuccessful -> {
                                        liveData.value = ResourceUser(
                                            data = null,
                                            error = context.getString(R.string.email_sent)
                                        )
                                    }
                                    else -> {
                                        liveData.value = ResourceUser(
                                            data = null,
                                            error = context.getString(R.string.unregistered_email)
                                        )
                                    }
                                }
                            }
                    }
                }
            } catch (e: Exception) {
                liveData.value = ResourceUser(data = null, error = e.message)
            }
        }
        return liveData
    }
}