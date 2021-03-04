package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfigurationViewModel(private val repository: BaseRepositoryUser) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UserFirebase())
    val uiStateFlow: StateFlow<UserFirebase> get() = _uiStateFlow

    fun getUserDBFirebase(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            repository.baseRepositoryGetCurrentUserFirebase(context) { userFirebase ->
                _uiStateFlow.value = userFirebase
            }
        }
    }

    fun imageUser(context: Context, image: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.baseRepositoryChangeImageUser(context, image)
        }
    }

    fun logout(context: Context) {
        repository.baseRepositoryLogoutFirebase(context)
    }
}
