package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.repository.BaseRepositoryUser.baseRepositoryChangeImageUser
import com.example.photoday.repository.BaseRepositoryUser.baseRepositoryGetCurrentUserFirebase
import com.example.photoday.repository.BaseRepositoryUser.baseRepositoryLogoutFirebase
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfigurationViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UserFirebase())
    val uiStateFlow: StateFlow<UserFirebase> get() = _uiStateFlow

    fun getUserDBFirebase() = viewModelScope.launch {
        val userFirebase = baseRepositoryGetCurrentUserFirebase()
        _uiStateFlow.value = userFirebase
    }

    fun imageUser(context: Context, image: Uri) {
        baseRepositoryChangeImageUser(context, image)
    }

    fun logout(context: Context) {
        baseRepositoryLogoutFirebase(context)
    }
}
