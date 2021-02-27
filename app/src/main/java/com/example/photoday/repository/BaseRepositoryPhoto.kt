package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.deleteImage
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.listFileDownload
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.uploadImageToStorage

object BaseRepositoryPhoto {
    fun baseRepositoryUploadImageToStorage(context: Context, dateCalendar: String, curFile: Uri?) {
        uploadImageToStorage(context, dateCalendar, curFile)
    }

    fun baseRepositoryDeleteImage(context: Context, date: String) {
        deleteImage(context, date)
    }

    fun baseRepositoryListFileDownload(bindingGallery: FragmentGalleryBinding?, context: Context, bindingTimeline: FragmentTimelineBinding?) =
        listFileDownload(bindingGallery, context, bindingTimeline)
}