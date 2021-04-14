package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.model.resource.ResourceUser

class ConfigurationViewModel(
    private val repository: BaseRepositoryUser,
    private val navFragment: NavController,
    private val context: Context
) : ViewModel() {


    fun getUserDBFirebase() = repository.baseRepositoryGetCurrentUserFirebase()

    fun imageUser(image: Uri) =
        repository.baseRepositoryChangeImageUser(image)

    fun logout(): LiveData<ResourceUser<Void>> {
        val logout = repository.baseRepositoryLogoutFirebase(context)
        Navigation.navFragmentConfigurationToSplashGoodbye(navFragment)
        return logout
    }

}
