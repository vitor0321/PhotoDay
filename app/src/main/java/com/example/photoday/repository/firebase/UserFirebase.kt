package com.example.photoday.repository.firebase

import android.widget.ImageView
import java.util.*

data class UserFirebase(
    var name: String? = null,
    var email: String? = null,
    var photo: ImageView? = null
)