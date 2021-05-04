package com.example.photoday.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.REGISTER
import com.example.photoday.ui.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryUser
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    fun updateUI(login: Int) =
        repository.baseRepositoryUpdateUI(login)

    fun signInWithEmailAndPassword(email: String, password: String) =
        repository.baseRepositorySignInWithEmailAndPassword(email, password)

    fun authWithGoogle(account: GoogleSignInAccount) =
        repository.baseRepositoryFirebaseAuthWithGoogle(
            account.idToken!!
        )

    fun forgotPassword(email: String) =
        repository.baseRepositoryForgotPassword(email)

    fun navController(navigation: Int) {
        when (navigation) {
            ON_START -> {
                Navigation.navFragmentLoginToTimeline(navFragment)
            }
            FIRST_LOGIN -> {
                Navigation.navFragmentLoginToSplashLogin(navFragment)
            }
            REGISTER -> {
                Navigation.navFragmentLoginToRegister(navFragment)
            }
        }
    }
}
