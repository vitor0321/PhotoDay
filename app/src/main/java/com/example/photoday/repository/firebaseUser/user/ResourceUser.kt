package com.example.photoday.repository.firebaseUser.user

class ResourceUser<T>(
    val data: T?,
    val error: String? = null,
    val login: Int? = null
)