package com.example.photoday.repository.firebase

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.example.photoday.constants.FIRST_LOGIN
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.Utils.toast
import com.example.photoday.navigation.Navigation.navFragmentLoginToSplashLogin
import com.example.photoday.navigation.Navigation.navFragmentLoginToTimeline
import com.google.firebase.auth.FirebaseAuth

object CheckUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    fun updateUI(
            controlNavigation: NavController,
            startLog: Int,
            context: Context
    ) {
        try {
            val currentUser = auth.currentUser
            /*if the user is different from null, then he exists and can log in*/
            when {
                currentUser != null -> {
                    when {
                        currentUser.isEmailVerified -> {
                            /*if you are already logged in go to Timeline,
                            if you are going to log in for the first time go to Login*/
                            when (startLog) {
                                ON_START -> {
                                    navFragmentLoginToTimeline(controlNavigation)
                                }
                                FIRST_LOGIN -> {
                                    navFragmentLoginToSplashLogin(controlNavigation)
                                    toast(context, R.string.login_is_success)
                                }
                            }
                        }
                        else -> {
                            toast(context, R.string.verify_your_email_address)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.message?.let { toast(context, it.toInt()) }
        }
    }

    fun getCurrentUserFirebase(
            context: Context,
            textName: TextView,
            textEmail: TextView,
            imageUser: ImageView
    ) {
        try {
            val user = auth.currentUser
            auth?.let {
                textName.text = user?.displayName
                textEmail.text = user?.email

                Glide.with(context)
                        .load(user?.photoUrl)
                        .fitCenter()
                        .placeholder(R.drawable.ic_photo_edit)
                        .into(imageUser)
            }
        } catch (e: Exception) {
            e.message?.let { toast(context, it.toInt()) }
        }
    }
}