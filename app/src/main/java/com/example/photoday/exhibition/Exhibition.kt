package com.example.photoday.exhibition

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.example.photoday.constants.GALLERY_TYPE
import com.example.photoday.constants.REQUEST_GALLERY
import com.example.photoday.constants.REQUEST_IMAGE_CAPTURE
import com.example.photoday.ui.PhotoDayActivity

object Exhibition {

    fun galleryExhibition(activity: PhotoDayActivity) {
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = GALLERY_TYPE
        ActivityCompat.startActivityForResult(
            activity, intentGallery,
            REQUEST_GALLERY, null
        )
    }

    fun dispatchTakeExhibition(packageManage: PackageManager, activity: PhotoDayActivity) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManage)?.also {
                ActivityCompat.startActivityForResult(
                    activity,
                    takePictureIntent,
                    REQUEST_IMAGE_CAPTURE,
                    null
                )
            }
        }
    }
}