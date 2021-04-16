package com.example.photoday.model.resource

class ResourceUser<T>(
    val data: T? = null,
    val error: String? = null,
    val message: Int? = null,
    val login: Int? = null,
    val navigation: Boolean? = false
)