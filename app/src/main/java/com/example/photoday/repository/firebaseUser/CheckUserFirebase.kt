package com.example.photoday.repository.firebaseUser

import android.content.Context
import com.example.photoday.constants.FALSE_USER
import com.example.photoday.constants.TRUE_USER
import com.example.photoday.constants.Utils.toast
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CheckUserFirebase {
    private var auth = FirebaseAuth.getInstance()

    fun updateUI(context: Context, callback: (startLogin: Boolean) -> Unit) {
        try {
            val currentUser = auth.currentUser
            /*if the user is different from null, then he exists and can log in*/
            when {
                currentUser != null -> {
                    when {
                        currentUser.isEmailVerified -> {
                            callback.invoke(TRUE_USER)
                        }
                        else -> {
                            callback.invoke(FALSE_USER)
                        }

                    }
                }
            }
        } catch (e: Exception) {
            e.message?.let { toast(context, it.toInt()) }
        }
    }

    fun getCurrentUserFirebase(context: Context, callback: (userFirebase: UserFirebase) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = UserFirebase()
                val auth = auth.currentUser
                auth?.let {
                    user.name = auth.displayName
                    user.email = auth.email.toString()
                    user.image = auth.photoUrl
                }
                callback.invoke(user)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { toast(context, it.toInt()) }
                }
            }
        }
    }
}