package com.example.photoday.ui.fragment.configuration

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.GOODBYE
import com.example.photoday.repository.BaseRepositoryPreferences
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.model.user.UserFirebase
import com.example.photoday.ui.navigation.Navigation

class ConfigurationViewModel(
    private val repositoryUser: BaseRepositoryUser,
    private val navFragment: NavController,
    private val preferences: BaseRepositoryPreferences
): ViewModel() {

    fun getStatusSwitchPreferences(): MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            value = preferences.baseGetValuePreferencesGallery()
        }

    fun switchPreferencesGallery(bottomSwitch: String, check: Boolean) =
        preferences.switchPreferencesGallery(bottomSwitch, check)

    fun getUserDBFirebase() =
        repositoryUser.baseRepositoryGetCurrentUserFirebase()

    fun imageUser(image: Uri) =
        repositoryUser.baseRepositoryChangeImageUser(image)

    fun newNameUser(userFirebase: UserFirebase) =
        repositoryUser.baseRepositoryChangeNameUser(userFirebase)

    fun logout() =
        repositoryUser.baseRepositoryLogoutFirebase()

    fun navController(navigation: Int) {
        when (navigation) {
            GOODBYE -> {
                Navigation.navFragmentConfigurationToSplashGoodbye(navFragment)
            }
        }
    }

}