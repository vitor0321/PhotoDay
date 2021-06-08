package com.example.photoday.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("upLoadImageUri")
fun ImageView.upLoadImageAdapter(photo: String) {
    upLoadImage(photo)
}

