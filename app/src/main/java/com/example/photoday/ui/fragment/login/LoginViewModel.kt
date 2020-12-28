package com.example.photoday.ui.fragment.login

import android.content.Context
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.RC_SIGN_IN
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.ui.fragment.splash.SplashLoginDirections
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {

    fun navFragmentSplashLogin(navFragment: NavController) {
        /*Navigation between fragments Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToSplashLogin()
        navFragment.navigate(direction)
    }

    fun navFragmentTimeline(navFragment: NavController) {
        /*Navigation between fragments Directions*/
        val direction = SplashLoginDirections.actionSplashLoginToTimelineFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentRegister(navFragment: NavController) {
        /*Navigation between fragments Directions*/
        val direction = LoginFragmentDirections.actionLoginFragmentToRegister()
        navFragment.navigate(direction)
    }

    fun signIn(googleSignInClient: GoogleSignInClient, requireActivity: FragmentActivity) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(requireActivity, signInIntent, RC_SIGN_IN, null)
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        auth: FirebaseAuth,
        controlNavigation: NavController,
        requireActivity: FragmentActivity,
        context: Context?
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity) { task ->
                when {
                    task.isSuccessful -> {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user, controlNavigation, context)
                    }
                    else -> {
                        showToast(context, context!!.getString(R.string.auth_failed))
                    }
                }
            }
    }

    fun updateUI(
        currentUser: FirebaseUser?,
        controlNavigation: NavController,
        context: Context?
    ) {
        /*if the user is different from null, then he exists and can log in*/
        when {
            currentUser != null -> {
                when {
                    currentUser.isEmailVerified -> {
                        navFragmentSplashLogin(controlNavigation)
                    }
                    else -> {
                        showToast(context, context!!.getString(R.string.verify_your_email_address))
                    }
                }
            }
        }
    }

}