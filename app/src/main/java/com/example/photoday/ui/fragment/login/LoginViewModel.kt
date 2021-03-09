package com.example.photoday.ui.fragment.login

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.photoday.constants.FORGOT_PASSWORD
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val controlNavigation: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    private val _uiStateFlowMessage = MutableStateFlow("")
    val uiStateFlowMessage: StateFlow<String> get() = _uiStateFlowMessage

    fun doLogin(
        editTextLoginUser: TextInputEditText,
        editTextLoginPassword: TextInputEditText,
        requireActivity: FragmentActivity,
        context: Context
    ) {
        viewModelScope.launch {
            repository.baseRepositorySignInWithEmailAndPassword(
                editTextLoginUser,
                editTextLoginPassword,
                requireActivity,
                controlNavigation,
                context,
                callbackMessage = { message -> _uiStateFlowMessage.value = message }
            )
        }
    }

    fun repositoryUpdateUI(controlNavigation: NavController, ON_START: Int, context: Context?) {
        viewModelScope.launch {
            context?.let { context ->
                repository.baseRepositoryUpdateUI(controlNavigation,
                    ON_START,
                    context,
                    callbackMessage = { message -> _uiStateFlowMessage.value = message })
            }
        }
    }

    fun authWithGoogle(account: GoogleSignInAccount, context: Context?) {
        viewModelScope.launch {
            context?.let { context ->
                repository.baseRepositoryFirebaseAuthWithGoogle(
                    account.idToken!!,
                    controlNavigation,
                    context,
                    callbackMessage = { message -> _uiStateFlowMessage.value = message })
            }
        }
    }


    fun forgotPassword(activity: FragmentActivity?) {
        activity?.let { activity ->
            ForgotPasswordDialog.newInstance()
                .show(activity.supportFragmentManager, FORGOT_PASSWORD)
        }
    }
}
