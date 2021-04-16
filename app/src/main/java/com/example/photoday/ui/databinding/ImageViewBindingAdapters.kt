package com.example.photoday.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.photoday.ui.adapter.extension.upLoadImage

@BindingAdapter("upLoadImageUri")
fun ImageView.upLoadImageAdapter(photo: String) {
    upLoadImage(photo)
}

