package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfigurationViewModel(private val repository: BaseRepositoryUser) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UserFirebase())
    val uiStateFlow: StateFlow<UserFirebase> get() = _uiStateFlow

    private val _uiStateFlowMessage = MutableStateFlow("")
    val uiStateFlowMessage: StateFlow<String> get() = _uiStateFlowMessage

    fun getUserDBFirebase() {
        viewModelScope.launch {
            repository.baseRepositoryGetCurrentUserFirebase(
                callback = { userFirebase -> _uiStateFlow.value = userFirebase },
                callbackMessage = { message -> _uiStateFlowMessage.value = message }
            )
        }
    }

    fun imageUser(image: Uri) {
        viewModelScope.launch {
            repository.baseRepositoryChangeImageUser(image,
                callbackMessage = { message -> _uiStateFlowMessage.value = message })
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            repository.baseRepositoryLogoutFirebase(context,
                callbackMessage = { message -> _uiStateFlowMessage.value = message })
        }
    }
}
