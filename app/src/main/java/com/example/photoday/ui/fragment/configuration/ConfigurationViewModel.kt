package com.example.photoday.ui.fragment.configuration

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.GOODBYE
import com.example.photoday.ui.model.resource.ResourceUser
import com.example.photoday.ui.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryUser

class ConfigurationViewModel(
    private val repository: BaseRepositoryUser,
    private val navFragment: NavController,
): ViewModel() {

    fun getUserDBFirebase() = repository.baseRepositoryGetCurrentUserFirebase()

    fun imageUser(image: Uri) =
        repository.baseRepositoryChangeImageUser(image)

    fun logout(): LiveData<ResourceUser<Void>> =
        repository.baseRepositoryLogoutFirebase()

    fun navController(navigation: Int) {
        when (navigation) {
            GOODBYE -> {
                Navigation.navFragmentConfigurationToSplashGoodbye(navFragment)
            }
        }
    }
}