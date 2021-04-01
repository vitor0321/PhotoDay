package com.example.photoday.repository.firebaseUser.user

class ResourceUser<T>(
    val data: T? = null,
    val error: String? = null,
    val message: Int? = null,
    val login: Int? = null
)