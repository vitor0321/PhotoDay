package com.example.photoday.repository.user

import android.net.Uri

open class UserFirebase(
        var name: String? = null,
        var email: String = "",
        var image: Uri? = null
)