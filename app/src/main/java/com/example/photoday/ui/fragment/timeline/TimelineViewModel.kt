package com.example.photoday.ui.fragment.timeline

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.repository.BaseRepositoryPhoto.baseRepositoryListFileDownload
import com.example.photoday.repository.BaseRepositoryPhoto.baseRepositoryUploadImageToStorage

class TimelineViewModel : ViewModel() {

    fun createPullPhotos(
        bindingGallery: FragmentGalleryBinding?,
        context: Context,
        bindingTimeline: FragmentTimelineBinding
    ) {
        baseRepositoryListFileDownload(bindingGallery, context, bindingTimeline)
    }


    fun createPushPhoto(context: Context, dateCalendar: String, curFile: Uri?) {
        baseRepositoryUploadImageToStorage(context, dateCalendar, curFile)
    }
}