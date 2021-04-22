package com.example.photoday.ui.fragment.configuration

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.*
import com.example.photoday.model.resource.ResourceUser
import com.example.photoday.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.example.photoday.ui.dialog.NewUserNameDialog

class ConfigurationViewModel(
    private val repository: BaseRepositoryUser,
    private val navFragment: NavController,
) : ViewModel() {


    fun getUserDBFirebase() = repository.baseRepositoryGetCurrentUserFirebase()

    fun imageUser(image: Uri) =
        repository.baseRepositoryChangeImageUser(image)

    fun logout(): LiveData<ResourceUser<Void>> =
        repository.baseRepositoryLogoutFirebase()

    fun navController(navigation: Int) {
        when(navigation){
            GOODBYE -> {Navigation.navFragmentConfigurationToSplashGoodbye(navFragment)}
        }
    }
}
