package com.example.photoday.ui.common

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.photoday.constants.*
import com.example.photoday.eventBus.MessageEvent
import org.greenrobot.eventbus.EventBus

object ExhibitionCameraOrGallery {

    fun exhibitionCaptureImage(
        accessSelected: Int,
        valueDate: String?,
        activity: Activity,
        request: Int
    ) {
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = GALLERY_TYPE
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                when (PermissionChecker.PERMISSION_DENIED) {
                    PermissionChecker.checkSelfPermission(
                        activity, Manifest.permission.READ_EXTERNAL_STORAGE,
                    ),
                    -> {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                            ),
                            REQUEST_IMAGE_GALLERY_USER
                        )
                    }
                    else -> {
                        when (request) {
                            REQUEST_PHOTO_DAY -> {
                                when (accessSelected) {
                                    ADD_GALLERY -> {
                                        /*Use the EventBus to send the Date to Timeline Fragment*/
                                        val datePhoto = MessageEvent(valueDate)
                                        EventBus.getDefault().post(datePhoto)
                                        /*Here open the Gallery. Send the image to the Timeline Fragment*/
                                        ActivityCompat.startActivityForResult(
                                            activity, intentGallery,
                                            REQUEST_IMAGE_GALLERY, null
                                        )
                                    }
                                    ADD_CAMERA -> {
                                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intentTakePicture ->
                                            intentTakePicture.resolveActivity(activity.packageManager)
                                                ?.also {
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
                            }
                            REQUEST_CONFIGURATION -> {
                                when (accessSelected) {
                                    ADD_GALLERY -> {
                                        /*Here open the Gallery. Send the image to the user Configuration Fragment*/
                                        ActivityCompat.startActivityForResult(
                                            activity, intentGallery,
                                            REQUEST_IMAGE_GALLERY_USER, null
                                        )
                                    }
                                    ADD_CAMERA -> {
                                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intentTakePicture ->
                                            val packageManager = activity.packageManager
                                            intentTakePicture.resolveActivity(packageManager)
                                                ?.also {
                                                    /*Here open the Camera and send the image to the User in Configuration Fragment*/
                                                    ActivityCompat.startActivityForResult(
                                                        activity,
                                                        intentTakePicture,
                                                        REQUEST_IMAGE_CAPTURE_USER,
                                                        null
                                                    )
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}