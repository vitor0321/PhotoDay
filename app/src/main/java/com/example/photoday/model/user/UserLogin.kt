package com.example.photoday.model.user

data class UserLogin(
    var email: String,
    var password: String,
    var confirmPassword: String? = null
)