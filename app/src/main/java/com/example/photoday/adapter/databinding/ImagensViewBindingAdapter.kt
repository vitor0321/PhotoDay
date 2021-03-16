package com.example.photoday.adapter.databinding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.photoday.adapter.extension.upDateImage

@BindingAdapter("android:src")
fun ImageView.upLoadImageAdapter(photo: Uri) {
    upDateImage(photo)
}