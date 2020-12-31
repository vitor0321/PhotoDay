package com.example.photoday.ui.fragment.login

import android.content.Context
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.repository.firebase.FirebaseLogout.signInWithEmailAndPassword

class LoginViewModel : ViewModel() {

    fun doLogin(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText,
        requireActivity: FragmentActivity,
        context: Context?,
        controlNavigation: NavController
    ) {
        /*here you will authenticate your email and password*/
        when {
            loginUserId.text.toString().isEmpty() -> {
                loginUserId.error = context!!.getString(R.string.please_enter_email)
                loginUserId.requestFocus()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(loginUserId.text.toString()).matches() -> {
                loginUserId.error = context!!.getString(R.string.please_enter_valid_email)
                loginUserId.requestFocus()
                return
            }
            loginPassword.text.toString().isEmpty() -> {
                loginPassword.error = context!!.getString(R.string.please_enter_password)
                loginPassword.requestFocus()
                return
            }
        }
        signInWithEmailAndPassword(
            loginUserId,
            loginPassword,
            requireActivity,
            context,
            controlNavigation
        )
    }
}