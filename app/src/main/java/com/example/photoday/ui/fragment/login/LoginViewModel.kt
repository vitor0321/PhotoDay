package com.example.photoday.ui.fragment.login

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE_USER
import com.example.photoday.constants.FORGOT_PASSWORD
import com.example.photoday.constants.TRUE_USER
import com.example.photoday.constants.Utils.toast
import com.example.photoday.navigation.Navigation
import com.example.photoday.navigation.Navigation.navFragmentLoginToTimeline
import com.example.photoday.repository.BaseRepositoryUser.baseRepositoryFirebaseAuthWithGoogle
import com.example.photoday.repository.BaseRepositoryUser.baseRepositorySignInWithEmailAndPassword
import com.example.photoday.repository.BaseRepositoryUser.baseRepositoryUpdateUI
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val controlNavigation: NavController,
) : ViewModel() {

    fun doLogin(
        editTextLoginUser: TextInputEditText,
        editTextLoginPassword: TextInputEditText,
        context: Context?,
        requireActivity: FragmentActivity,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let { context ->
                baseRepositorySignInWithEmailAndPassword(
                    editTextLoginUser,
                    editTextLoginPassword,
                    requireActivity,
                context){

                }
            }
        }
    }

    fun repositoryUpdateUI(controlNavigation: NavController, context: Context?) {
        context?.let { context ->
            baseRepositoryUpdateUI(context) { startLogin ->
                when (startLogin) {
                    TRUE_USER -> {
                        navFragmentLoginToTimeline(controlNavigation)
                        toast(context, R.string.login_is_success)
                    }
                    FALSE_USER -> {
                        toast(context, R.string.verify_your_email_address)
                    }
                }
            }
        }
    }

    fun authWithGoogle(account: GoogleSignInAccount, context: Context?) {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let { context ->
                baseRepositoryFirebaseAuthWithGoogle(
                    account.idToken!!,
                    context
                ){

                }
            }
        }
    }

    fun forgotPassword(activity: FragmentActivity?) {
        activity?.let {
            ForgotPasswordDialog.newInstance()
                    .show(it.supportFragmentManager, FORGOT_PASSWORD)
        }
    }
}
