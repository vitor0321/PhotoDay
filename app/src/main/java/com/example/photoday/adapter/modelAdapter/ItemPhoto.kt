package com.example.photoday.adapter.modelAdapter

import androidx.annotation.DrawableRes

data class ItemPhoto(
        val date: String,
        @DrawableRes
        val photo: Int
)