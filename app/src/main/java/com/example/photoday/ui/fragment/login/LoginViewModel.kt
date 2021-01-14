package com.example.photoday.ui.fragment.login

import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.repository.firebase.FirebaseLogout
import com.example.photoday.repository.firebase.FirebaseLogout.signInWithEmailAndPassword
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginViewModel(
    private val controlNavigation: NavController,
    private val context: Context?,
    private val requireActivity: FragmentActivity,
    private val lifecycleScope: LifecycleCoroutineScope
) : ViewModel() {

    fun doLogin(
        loginUserId: AppCompatEditText,
        loginPassword: AppCompatEditText
    ) {
        /*here you will authenticate your email and password*/
        when {
            loginUserId.text.toString().isEmpty() -> {
                loginUserId.error = context?.getString(R.string.please_enter_email_login)
                loginUserId.requestFocus()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(loginUserId.text.toString()).matches() -> {
                loginUserId.error = context?.getString(R.string.please_enter_valid_email_login)
                loginUserId.requestFocus()
                return
            }
            loginPassword.text.toString().isEmpty() -> {
                loginPassword.error = context?.getString(R.string.please_enter_password)
                loginPassword.requestFocus()
                return
            }
        }
        context?.let { context ->
            signInWithEmailAndPassword(
                loginUserId,
                loginPassword,
                requireActivity,
                controlNavigation,
                context,
                lifecycleScope
            )
        }
    }

    fun authWithGoogle(account: GoogleSignInAccount) {
        context?.let { context ->
            FirebaseLogout.firebaseAuthWithGoogle(
                account.idToken!!,
                controlNavigation,
                context,
                lifecycleScope
            )
        }
    }

    fun alertDialogForgotPassword(layoutInflater: LayoutInflater) {
        /*Alert Dialog Forgot the password*/
        val builder = context?.let { context -> AlertDialog.Builder(context, R.style.DialogTheme) }
        builder?.setTitle(context?.getString(R.string.what_is_your_email))
        val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
        val userEmail = view.findViewById<EditText>(R.id.edit_text_email_confirm)
        builder?.setView(view)
        builder?.setPositiveButton(context?.getString(R.string.ok)) { _, _ ->
            context?.let { context -> FirebaseLogout.forgotPassword(context, userEmail) }
        }
        builder?.setNegativeButton(context?.getString(R.string.cancel)) { _, _ -> }
        builder?.show()
    }
}
