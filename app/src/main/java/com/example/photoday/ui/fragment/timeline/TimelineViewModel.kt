package com.example.photoday.ui.fragment.timeline

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.repository.BaseRepositoryPhoto.baseRepositoryListFileDownload

class TimelineViewModel : ViewModel() {

    fun createPullPhotos(
        bindingGallery: FragmentGalleryBinding?,
        context: Context,
        bindingTimeline: FragmentTimelineBinding
    ) {
        baseRepositoryListFileDownload(bindingGallery, context, bindingTimeline)
    }
}