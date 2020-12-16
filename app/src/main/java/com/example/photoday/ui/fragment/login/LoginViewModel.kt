package com.example.photoday.ui.fragment.login

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.RC_SIGN_IN
import com.example.photoday.constants.Uteis
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.fragment.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(private val repository: LoginRepositoryShared) : ViewModel() {

    fun login() = repository.login()

    fun loginIn(): Boolean = repository.loginIn()

    fun logout() = repository.logout()

    fun noIsLogin(): Boolean = !loginIn()

    fun goToLogin(controlNavigation: NavController) {
        val direction = LoginFragmentDirections.actionLoginFragmentToTimelineFragment()
        controlNavigation.navigate(direction)
    }

    fun navFragmentLogin(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToSplashLogin()
        navFragment.navigate(direction)
    }

    fun navFragmentRegister(navFragment: NavController) {
        /*navegando entre fragment usando o Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToRegister()
        navFragment.navigate(direction)
    }

    fun loginStatus(controlNavigation: NavController) {
        when (noIsLogin()) {
            false -> goToLogin(controlNavigation)
        }
    }

    fun signIn(googleSignInClient: GoogleSignInClient, requireActivity: FragmentActivity) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(requireActivity, signInIntent, RC_SIGN_IN, null)
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        auth: FirebaseAuth,
        controlNavigation: NavController,
        requireActivity: FragmentActivity
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    login()
                    navFragmentLogin(controlNavigation)
                } else {
                    Uteis.showToast(requireActivity, "Sorry, auth failed.")
                }
            }
    }
}