package com.example.photoday.ui.fragment.timeline

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.REQUEST_GALLERY_TIMELINE
import com.example.photoday.constants.REQUEST_IMAGE_CAPTURE_TIMELINE
import com.example.photoday.constants.TRUE
import com.example.photoday.constants.Utils
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.ui.adapter.TimelineListAdapter
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import java.io.ByteArrayOutputStream

class TimelineFragment : BaseFragment() {

    private lateinit var binding: FragmentTimelineBinding
    private val viewModel by lazy { ViewModelInjector.providerTimelineViewModel() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_timeline, menu)
    }

    private fun init() {
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        binding.apply {
            viewModel.photosLiveData.observe(viewLifecycleOwner, {
                recycleViewListTimeline.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = TimelineListAdapter(it)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            //here get the image of Exhibition
            when {
                requestCode == REQUEST_GALLERY_TIMELINE && resultCode == Activity.RESULT_OK -> {
                    data?.data?.let {
                        viewModel.createPushPhotos(it)
                    }
                }
                requestCode == REQUEST_IMAGE_CAPTURE_TIMELINE && resultCode == Activity.RESULT_OK -> {
                    val imageBitmap = data?.extras?.get(ContactsContract.Intents.Insert.DATA) as Bitmap
                    val bytes = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    val path: String = MediaStore.Images.Media.insertImage(
                            context?.contentResolver,
                            imageBitmap,
                            getString(R.string.change_image_user),
                            null)
                    viewModel.createPushPhotos(Uri.parse(path))
                }
            }
        } catch (e: Exception) {
            e.message?.let { context?.let { context -> Utils.toast(context, it.toInt()) } }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(true, Components(TRUE, TRUE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        binding
        super.onDestroy()
    }
}