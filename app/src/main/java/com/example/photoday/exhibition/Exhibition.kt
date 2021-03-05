package com.example.photoday.exhibition

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.example.photoday.constants.*
import com.example.photoday.constants.Utils.toast
import com.example.photoday.eventBus.MessageEvent
import com.example.photoday.ui.activity.PhotoDayActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus

object Exhibition {

    suspend fun galleryExhibition(activity: PhotoDayActivity, valueDate: String?) {
        try {
            val intentGallery = Intent(Intent.ACTION_PICK)
            intentGallery.type = GALLERY_TYPE
            when (valueDate) {
                null -> {
                    /*Here open the Gallery. Send the image to the user Configuration Fragment*/
                    ActivityCompat.startActivityForResult(
                        activity, intentGallery,
                        REQUEST_IMAGE_GALLERY_USER, null
                    )
                }
                else -> {
                    /*Use the EventBus to send the Date to Timeline Fragment*/
                    val datePhoto = MessageEvent(valueDate)
                    EventBus.getDefault().post(datePhoto)
                    /*Here open the Gallery. Send the image to the Timeline Fragment*/
                    ActivityCompat.startActivityForResult(
                        activity, intentGallery,
                        REQUEST_IMAGE_GALLERY, null
                    )
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(activity, message.toInt()) }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    suspend fun dispatchTakeExhibition(activity: PhotoDayActivity, valueDate: String?) {
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
                                REQUEST_IMAGE_CAPTURE,
                                null
                            )
                        }
                    }
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(activity, message.toInt()) }
            }
        }
    }
}