package com.example.photoday.ui.model.user

import android.net.Uri

data class UserFirebase(
        var name: String? = null,
        var email: String? = null,
        var image: Uri? = null
)