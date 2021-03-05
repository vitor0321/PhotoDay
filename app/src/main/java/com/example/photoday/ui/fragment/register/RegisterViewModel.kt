package com.example.photoday.ui.fragment.register

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val controlNavigation: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    fun signUpUser(
        registerUser: AppCompatEditText,
        registerUserPassword: AppCompatEditText,
        context: Context?,
    ) {
        viewModelScope.launch {
            context?.let {
                repository.baseRepositoryCreateUserWithEmailAndPassword(
                    context,
                    registerUser,
                    registerUserPassword,
                    controlNavigation
                )
            }
        }
    }
}


