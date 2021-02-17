package com.example.photoday.exhibition

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.example.photoday.constants.*
import com.example.photoday.ui.PhotoDayActivity

object Exhibition {

    fun galleryExhibition(activity: PhotoDayActivity, valueDate: String?) {
        try {
            val intentGallery = Intent(Intent.ACTION_PICK)
            intentGallery.type = GALLERY_TYPE
            when (valueDate) {
                null -> {
                    ActivityCompat.startActivityForResult(
                            activity, intentGallery,
                            REQUEST_GALLERY_USER, null
                    )
                }
                else -> {
                    val dateBundle = Bundle()
                    dateBundle.putString(BUNDLE_DATE, valueDate)
                    intentGallery.putExtras(dateBundle)
                    intentGallery.putExtra(BUNDLE_DATE, valueDate)
                    ActivityCompat.startActivityForResult(
                            activity, intentGallery,
                            REQUEST_GALLERY_TIMELINE, dateBundle
                    )
                }
            }
        } catch (e: Exception) {
            e.message?.let { Utils.toast(activity, it.toInt()) }
        }
    }

    fun dispatchTakeExhibition(activity: PhotoDayActivity, valueDate: String?) {
        try {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                val packageManager = activity.packageManager
                when (valueDate) {
                    null -> {
                        takePictureIntent.resolveActivity(packageManager)?.also {
                            ActivityCompat.startActivityForResult(
                                    activity,
                                    takePictureIntent,
                                    REQUEST_IMAGE_CAPTURE_USER,
                                    null
                            )
                        }
                    }
                    else -> {
                        takePictureIntent.resolveActivity(packageManager)?.also {
                            ActivityCompat.startActivityForResult(
                                    activity,
                                    takePictureIntent,
                                    REQUEST_IMAGE_CAPTURE_TIMELINE,
                                    null
                            )
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.message?.let { Utils.toast(activity, it.toInt()) }
        }
    }
}