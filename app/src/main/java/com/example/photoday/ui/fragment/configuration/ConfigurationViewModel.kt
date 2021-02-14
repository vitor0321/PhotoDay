package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.repository.firebase.ChangeUserFirebase.changeImageUser
import com.example.photoday.repository.firebase.CheckUserFirebase.getCurrentUserFirebase
import com.example.photoday.repository.firebase.LogFirebase.logoutFirebase
import com.example.photoday.repository.user.UserFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfigurationViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UserFirebase())
    val uiStateFlow: StateFlow<UserFirebase> get() = _uiStateFlow

    fun getUserDBFirebase() = viewModelScope.launch {
        val userFirebase = getCurrentUserFirebase()
        _uiStateFlow.value = userFirebase
    }

    fun imageUser(context: Context, image: Uri) {
        changeImageUser(context, image)
    }

    fun logout(context: Context) {
        logoutFirebase(context)
    }
}
