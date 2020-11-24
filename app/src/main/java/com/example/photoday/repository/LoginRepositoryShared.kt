package com.example.photoday.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.photoday.constants.LOGIN_IN

class LoginRepositoryShared(private val preferences: SharedPreferences) {

    fun login() = salve(true)

    fun loginIn(): Boolean = preferences.getBoolean(LOGIN_IN, false)

    fun logout() = salve(false)

    private fun salve(state: Boolean) {
        preferences.edit {
            putBoolean(LOGIN_IN, state)
        }
    }
}