package com.example.photoday.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.photoday.R
import com.example.photoday.constants.REQUEST_GALLERY
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.exhibition.Exhibition.galleryExhibition
import com.example.photoday.ui.MainActivity

object CheckVersionPermission {

    fun galleryPermission(context: Context, activity: MainActivity) {
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
        } else showToast(context, context.getString(R.string.version_less_23))
    }
}