package com.example.photoday.ui.fragment.login

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FORGOT_PASSWORD
import com.example.photoday.repository.BaseRepository.baseRepositoryFirebaseAuthWithGoogle
import com.example.photoday.repository.BaseRepository.baseRepositorySignInWithEmailAndPassword
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.textfield.TextInputEditText

class LoginViewModel(
        private val controlNavigation: NavController,
        private val context: Context?,
        private val requireActivity: FragmentActivity,
) : ViewModel() {

    fun doLogin(editTextLoginUser: TextInputEditText, editTextLoginPassword: TextInputEditText) {
        context?.let {
            baseRepositorySignInWithEmailAndPassword(
                    editTextLoginUser,
                    editTextLoginPassword,
                    requireActivity,
                    controlNavigation,
                    it
            )
        }
    }

    fun authWithGoogle(account: GoogleSignInAccount) {
        context?.let {
            baseRepositoryFirebaseAuthWithGoogle(
                    account.idToken!!,
                    controlNavigation,
                    it
            )
        }
    }

    fun forgotPassword(activity: FragmentActivity?) {
        activity?.let {
            ForgotPasswordDialog.newInstance()
                    .show(it.supportFragmentManager, FORGOT_PASSWORD)
        }
    }
}
