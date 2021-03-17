package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryUser

class ConfigurationViewModel(private val repository: BaseRepositoryUser) : ViewModel() {


    fun getUserDBFirebase() = repository.baseRepositoryGetCurrentUserFirebase()

    fun imageUser(image: Uri, context: Context) =
        repository.baseRepositoryChangeImageUser(image, context)

    fun logout(context: Context) = repository.baseRepositoryLogoutFirebase(context)

}
