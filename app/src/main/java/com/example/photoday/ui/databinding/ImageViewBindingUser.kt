package com.example.photoday.ui.databinding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.photoday.ui.adapter.extension.upLoadImageUserFirebase


@BindingAdapter("upLoadImageUser")
fun ImageView.upLoadImageUser(photo: Uri?) {
    photo?.let{ upLoadImageUserFirebase(photo) }
}
