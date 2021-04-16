package com.example.photoday.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.FORGOT_PASSWORD
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.REGISTER
import com.example.photoday.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    private val _uiStateFlowMessage = MutableStateFlow("")
    val uiStateFlowMessage: StateFlow<String> get() = _uiStateFlowMessage

    fun updateUI(login: Int) =
        repository.baseRepositoryUpdateUI(login)

    fun signInWithEmailAndPassword(email: String, password: String) =
        repository.baseRepositorySignInWithEmailAndPassword(email, password)

    fun authWithGoogle(account: GoogleSignInAccount) =
        repository.baseRepositoryFirebaseAuthWithGoogle(
            account.idToken!!
        )

    fun navController(navigation: Int){
        when(navigation){
            ON_START ->{Navigation.navFragmentLoginToTimeline(navFragment)}
            FIRST_LOGIN ->{Navigation.navFragmentLoginToSplashLogin(navFragment)}
            REGISTER -> {Navigation.navFragmentLoginToRegister(navFragment)}
            FORGOT_PASSWORD -> {ForgotPasswordDialog.newInstance(repository)}
        }
    }
}
