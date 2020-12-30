package com.example.photoday.ui.fragment.configuration

import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.google.firebase.auth.FirebaseAuth

class ConfigurationViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()

    /*set name, email and photo of the user*/
    fun googleSingIn(
        text_view_user_name: AppCompatTextView,
        text_view_user_email: AppCompatTextView
    ) {
        val googleSignIn = auth.currentUser
        when {
            googleSignIn != null -> {
                text_view_user_name.text = googleSignIn.displayName
                text_view_user_email.text = googleSignIn.email
            }
            else -> {
                text_view_user_name.text = R.string.name_user.toString()
                text_view_user_email.text = R.string.email_user.toString()
            }
        }
    }
}