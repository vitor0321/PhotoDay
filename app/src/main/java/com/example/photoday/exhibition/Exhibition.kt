package com.example.photoday.exhibition

import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.example.photoday.constants.GALLERY_TYPE
import com.example.photoday.constants.REQUEST_GALLERY
import com.example.photoday.constants.REQUEST_IMAGE_CAPTURE
import com.example.photoday.constants.Utils
import com.example.photoday.ui.PhotoDayActivity

object Exhibition {

    fun galleryExhibition(activity: PhotoDayActivity) {
        try {
            val intentGallery = Intent(Intent.ACTION_PICK)
            intentGallery.type = GALLERY_TYPE
            ActivityCompat.startActivityForResult(
                    activity, intentGallery,
                    REQUEST_GALLERY, null
            )
        }catch (e: Exception) {
            e.message?.let { Utils.toast(activity, it.toInt()) }
        }

    }

    fun dispatchTakeExhibition(activity: PhotoDayActivity) {
        try {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                val packageManager = activity.packageManager
                takePictureIntent.resolveActivity(packageManager)?.also {
                    ActivityCompat.startActivityForResult(
                            activity,
                            takePictureIntent,
                            REQUEST_IMAGE_CAPTURE,
                            null
                    )
                }
            }
        }catch (e: Exception) {
            e.message?.let { Utils.toast(activity, it.toInt()) }
        }
    }
}