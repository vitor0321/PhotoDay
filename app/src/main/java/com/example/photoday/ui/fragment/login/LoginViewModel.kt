package com.example.photoday.ui.fragment.login

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FIRSTLOGIN
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.ui.fragment.login.Logout.updateUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*

class LoginViewModel : ViewModel() {


    fun firebaseAuthWithGoogle(
        idToken: String,
        auth: FirebaseAuth,
        controlNavigation: NavController,
        context: Context?
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user, controlNavigation, context, FIRSTLOGIN)
                    }
                    else -> {
                        showToast(context, context!!.getString(R.string.auth_failed))
                    }
                }
            }
    }

    fun signInWithEmailAndPassword(
        auth: FirebaseAuth,
        login_user_id: AppCompatEditText,
        login_password: AppCompatEditText,
        requireActivity: FragmentActivity,
        context: Context?,
        controlNavigation: NavController
    ) {
        /*checking if the user exists*/
        auth.signInWithEmailAndPassword(
            login_user_id.text.toString(),
            login_password.text.toString()
        )
            .addOnCompleteListener(requireActivity) { task ->
                when {
                    task.isSuccessful -> {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user, controlNavigation, context, FIRSTLOGIN)
                    }
                    else -> {
                        // If sign in fails, display a message to the user.
                        showToast(context, R.string.login_failed.toString())
                        updateUI(null, controlNavigation, context, FIRSTLOGIN)
                    }
                }
            }
    }

    fun forgotPassword(userEmail: EditText, context: Context?, auth: FirebaseAuth) {
        when {
            userEmail.text.toString().isEmpty() -> {
                showToast(context, R.string.please_enter_email.toString())
            }
            !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                showToast(context, R.string.please_enter_valid_email.toString())
            }
            else -> {
                auth.sendPasswordResetEmail(userEmail.text.toString())
                    .addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                showToast(context, R.string.email_sent.toString())
                            }
                            !task.isSuccessful -> {
                                showToast(context, R.string.unregistered_email.toString())
                            }
                        }
                    }
            }
        }
    }
}