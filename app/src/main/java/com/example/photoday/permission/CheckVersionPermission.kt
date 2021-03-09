package com.example.photoday.permission

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.photoday.R
import com.example.photoday.constants.REQUEST_IMAGE_CAPTURE_USER
import com.example.photoday.constants.REQUEST_IMAGE_GALLERY_USER
import com.example.photoday.exhibition.Exhibition.dispatchTakeExhibition
import com.example.photoday.exhibition.Exhibition.galleryExhibition
import com.example.photoday.ui.activity.PhotoDayActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CheckVersionPermission {
    fun galleryPermission(
        activity: PhotoDayActivity,
        valueDate: String?,
        context: Context,
        callbackMessage: (message: String) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    when (PermissionChecker.PERMISSION_DENIED) {
                        PermissionChecker.checkSelfPermission(
                            activity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        -> {
                            ActivityCompat.requestPermissions(
                                activity,
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ),
                                REQUEST_IMAGE_GALLERY_USER
                            )
                        }
                        else -> galleryExhibition(activity, valueDate,
                            callbackMessage = { message -> callbackMessage.invoke(message) })
                    }
                } else callbackMessage.invoke(context.getString(R.string.version_less_23))
            } catch (e: Exception) {
                e.message?.let { message -> callbackMessage.invoke(message) }
            }
        }
    }

    fun dispatchTakePermission(
        activity: PhotoDayActivity,
        valueDate: String?,
        context: Context,
        callbackMessage: (message: String) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    when (PermissionChecker.PERMISSION_DENIED) {
                        PermissionChecker.checkSelfPermission(
                            activity,
                            Manifest.permission.CAMERA
                        ),
                        -> {
                            ActivityCompat.requestPermissions(
                                activity,
                                arrayOf(Manifest.permission.CAMERA),
                                REQUEST_IMAGE_CAPTURE_USER
                            )
                        }
                        else -> dispatchTakeExhibition(activity, valueDate,
                        callbackMessage = {message -> callbackMessage.invoke(message) })
                    }
                } else
                    callbackMessage.invoke(context.getString(R.string.version_less_23))
            } catch (e: Exception) {
                    e.message?.let { message -> callbackMessage.invoke(message) }
            }
        }
    }
}