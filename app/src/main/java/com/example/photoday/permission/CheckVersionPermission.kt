package com.example.photoday.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.photoday.R
import com.example.photoday.constants.REQUEST_GALLERY
import com.example.photoday.constants.REQUEST_IMAGE_CAPTURE
import com.example.photoday.constants.Utils.toast
import com.example.photoday.exhibition.Exhibition.dispatchTakeExhibition
import com.example.photoday.exhibition.Exhibition.galleryExhibition
import com.example.photoday.ui.PhotoDayActivity

object CheckVersionPermission {
    fun galleryPermission(context: Context, activity: PhotoDayActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                PermissionChecker.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PermissionChecker.PERMISSION_DENIED -> {
                    val permisoArchivos = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(
                        Activity(),
                        permisoArchivos,
                        REQUEST_GALLERY
                    )
                }
                else -> galleryExhibition(activity)
            }
        } else toast(context, R.string.version_less_23)
    }

    fun dispatchTakePermission(
        context: Context,
        packageManage: PackageManager,
        activity: PhotoDayActivity
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                PermissionChecker.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PermissionChecker.PERMISSION_DENIED -> {
                    val permisoArchivos = arrayOf(Manifest.permission.CAMERA)
                    ActivityCompat.requestPermissions(
                        Activity(),
                        permisoArchivos,
                        REQUEST_IMAGE_CAPTURE
                    )
                }
                else -> dispatchTakeExhibition(packageManage, activity)
            }
        } else
        toast(context, R.string.version_less_23)
    }
}