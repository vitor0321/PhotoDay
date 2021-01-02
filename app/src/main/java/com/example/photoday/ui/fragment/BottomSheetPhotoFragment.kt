package com.example.photoday.ui.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract.Intents.Insert.DATA
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.constants.Utils.toast
import com.example.photoday.permission.CheckVersionPermission.dispatchTakePermission
import com.example.photoday.permission.CheckVersionPermission.galleryPermission
import com.example.photoday.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_botton_sheet_photo.*
import kotlinx.android.synthetic.main.fragment_configuration.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetPhotoFragment : BottomSheetDialogFragment() {
    var currentPath: String? = null
    val activity = requireActivity() as MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_botton_sheet_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == TAKE_PICTURE && resultCode == AppCompatActivity.RESULT_OK -> {
                try {
                    val imageBitmap = data?.extras?.get(DATA) as Bitmap
                    image_user.setImageBitmap(imageBitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            requestCode == SELECT_PICTURE && resultCode == AppCompatActivity.RESULT_OK -> {
                try {
                    image_user.setImageURI(data?.data)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_GALLERY -> {
                when (grantResults[0]) {
                    PackageManager.PERMISSION_GRANTED -> {
                        context?.let {
                            galleryPermission(
                                it,
                                activity
                            )
                        }
                    }
                    else -> {
                        context?.let { context -> toast(context, R.string.cannot_access_gallery) }
                    }
                }
            }
        }
    }


    private fun init() {
        /*open the camera*/
        image_upload_camera.setOnClickListener {
            dispatchCameraIntent(activity)
        }

        /*open the gallery*/
        image_upload_gallery.setOnClickListener {
            dispatchGalleryIntent(activity)
        }
    }

    private fun dispatchCameraIntent(activity: MainActivity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        when {
            intent.resolveActivity(activity.packageManager) != null -> {
                var photoFile: File? = null
                try {
                    photoFile = createImage()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                when {
                    photoFile != null -> {
                        var photoUri = context?.let { context ->
                            FileProvider.getUriForFile(
                                context,
                                CAMERA_FILE,
                                photoFile
                            )
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
                            startActivityForResult(intent, TAKE_PICTURE)
                        }
                    }
                }
            }
        }
        context?.let { context ->
            dispatchTakePermission(
                context, activity.packageManager, activity
            )
        }
    }

    private fun createImage(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "JPEG" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageName, ".jpg", storageDir)
        currentPath = image.absolutePath
        return image
    }

    private fun dispatchGalleryIntent(activity: MainActivity) {
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = GALLERY_TYPE
        startActivityForResult(Intent.createChooser(intentGallery, "Select Image"), SELECT_PICTURE)

        context?.let { context -> galleryPermission(context, activity) }
    }
}

