package com.example.photoday.repository.firebaseUser.user

data class ItemLogin(
    var email: String? = null,
    var password: String? = null,
    var confirmPassword: String? = null
)