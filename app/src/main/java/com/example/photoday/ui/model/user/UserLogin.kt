package com.example.photoday.ui.model.user

data class UserLogin(
    var email: String,
    var password: String,
    var confirmPassword: String? = null
)