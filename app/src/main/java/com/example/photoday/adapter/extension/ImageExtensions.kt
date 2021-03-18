package com.example.photoday.adapter.extension

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.upDateImage(photo: Uri) {
    Glide.with(this)
        .load(photo)
        .centerCrop()
        .into(this)
    //Picasso.get().load(photo).into(view)
    //view.setImageURI(photo)
}