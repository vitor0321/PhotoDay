package com.example.photoday.ui.adapter.extension

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

fun ImageView.upLoadImage(photo: String) {
    Picasso.get().load(photo).into(this)
}

fun ImageView.upLoadImageUserFirebase(photo: Uri) {
    Glide.with(context)
        .load(photo)
        .fitCenter()
        .placeholder(R.drawable.ic_photo_edit)
        .into(this)
}