package com.example.photoday.ui.fragment.login

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.constants.FORGOT_PASSWORD
import com.example.photoday.repository.firebase.FirebaseLogout
import com.example.photoday.repository.firebase.FirebaseLogout.signInWithEmailAndPassword
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.textfield.TextInputEditText

class LoginViewModel(
    private val controlNavigation: NavController,
    private val context: Context?,
    private val requireActivity: FragmentActivity,
    private val lifecycleScope: LifecycleCoroutineScope
) : ViewModel() {

    fun doLogin(editTextLoginUser: TextInputEditText, editTextLoginPassword: TextInputEditText) {
        context?.let {
            signInWithEmailAndPassword(
                editTextLoginUser,
                editTextLoginPassword,
                requireActivity,
                controlNavigation,
                it,
                lifecycleScope
            )
        }
    }

    fun authWithGoogle(account: GoogleSignInAccount) {
        context?.let {
            FirebaseLogout.firebaseAuthWithGoogle(
                account.idToken!!,
                controlNavigation,
                it,
                lifecycleScope
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
