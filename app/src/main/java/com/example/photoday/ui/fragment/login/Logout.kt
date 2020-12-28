package com.example.photoday.ui.fragment.login

import android.content.Context
import com.example.photoday.R
import com.example.photoday.constants.Uteis.showToast
import com.firebase.ui.auth.AuthUI

object Logout {
    fun logout(context: Context) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnSuccessListener {
                showToast(context, context.getString(R.string.successfully_logged))
            }
    }
}