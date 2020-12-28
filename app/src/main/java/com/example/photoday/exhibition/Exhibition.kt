package com.example.photoday.exhibition

import android.content.Intent
import androidx.core.app.ActivityCompat
import com.example.photoday.constants.GALLERY_TYPE
import com.example.photoday.constants.REQUEST_GALLERY
import com.example.photoday.ui.MainActivity

object Exhibition {

    fun galleryExhibition(activity: MainActivity) {
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = GALLERY_TYPE
        ActivityCompat.startActivityForResult(
            activity, intentGallery,
            REQUEST_GALLERY, null
        )
    }
}