package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val controlNavigation: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    private val _uiStateFlowMessage = MutableStateFlow("")
    val uiStateFlowMessage: StateFlow<String> get() = _uiStateFlowMessage

    fun signUpUser(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        context: Context?,
    ) {
        viewModelScope.launch {
            context?.let {
                repository.baseRepositoryCreateUserWithEmailAndPassword(
                    registerUser,
                    registerUserPassword,
                    controlNavigation,
                    context,
                    callbackMessage = { message -> _uiStateFlowMessage.value = message }
                )
            }
        }
    }
}


