package com.example.photoday.exhibition

import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.example.photoday.constants.*
import com.example.photoday.eventBus.MessageEvent
import com.example.photoday.ui.PhotoDayActivity
import org.greenrobot.eventbus.EventBus


object Exhibition {

    fun galleryExhibition(activity: PhotoDayActivity, valueDate: String) {
        try {
            val intentGallery = Intent(Intent.ACTION_PICK)
            intentGallery.type = GALLERY_TYPE
            when (valueDate) {
                null -> {
                    /*Here open the Gallery. Send the image to the user Configuration Fragment*/
                    ActivityCompat.startActivityForResult(
                        activity, intentGallery,
                        REQUEST_GALLERY_USER, null
                    )
                }
                else -> {
                    /*Use the EventBus to send the Date to Timeline Fragment*/
                    val datePhoto = MessageEvent(valueDate)
                    EventBus.getDefault().post(datePhoto)
                    /*Here open the Gallery. Send the image to the Timeline Fragment*/
                    ActivityCompat.startActivityForResult(
                        activity, intentGallery,
                        REQUEST_GALLERY_TIMELINE, null
                    )
                }
            }
        } catch (e: Exception) {
            e.message?.let { Utils.toast(activity, it.toInt()) }
        }
    }

    fun dispatchTakeExhibition(activity: PhotoDayActivity, valueDate: String) {
        try {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intentTakePicture ->
                val packageManager = activity.packageManager
                when (valueDate) {
                    null -> {
                        intentTakePicture.resolveActivity(packageManager)?.also {
                            /*Here open the Camera and send the image to the User in Configuration Fragment*/
                            ActivityCompat.startActivityForResult(
                                activity,
                                intentTakePicture,
                                REQUEST_IMAGE_CAPTURE_USER,
                                null
                            )
                        }
                    }
                    else -> {
                        intentTakePicture.resolveActivity(packageManager)?.also {
                            /*Use the EventBus to send the Date to Timeline Fragment*/
                            val datePhoto = MessageEvent(valueDate)
                            EventBus.getDefault().post(datePhoto)
                            /*Here open the Camera and send the image to the Timeline Fragment*/
                            ActivityCompat.startActivityForResult(
                                activity,
                                intentTakePicture,
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