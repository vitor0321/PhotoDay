package com.example.photoday.ui.fragment.login

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FORGOT_PASSWORD
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    private val _uiStateFlowMessage = MutableStateFlow("")
    val uiStateFlowMessage: StateFlow<String> get() = _uiStateFlowMessage

    fun updateUI(controlNavigation: NavController, ON_START: Int) =
        repository.baseRepositoryUpdateUI(controlNavigation, ON_START)

    fun doLogin(
        editTextLoginUser: TextInputEditText,
        editTextLoginPassword: TextInputEditText,
        requireActivity: FragmentActivity,
    ) = repository.baseRepositorySignInWithEmailAndPassword(
        editTextLoginUser,
        editTextLoginPassword,
        requireActivity,
        navFragment
    )

    fun authWithGoogle(account: GoogleSignInAccount) =
        repository.baseRepositoryFirebaseAuthWithGoogle(
            account.idToken!!,
            navFragment
        )


    fun forgotPassword() {
        ForgotPasswordDialog.newInstance()
    }
}
