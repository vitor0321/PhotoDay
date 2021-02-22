package com.example.photoday.ui.fragment.timeline

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.eventBus.MessageEvent
import com.example.photoday.ui.adapter.TimelineListAdapter
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.ByteArrayOutputStream


class TimelineFragment : BaseFragment() {

    private var datePhotoEventBus: String? = null
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

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(datePhoto: MessageEvent) {
        /*The Event Bus get date that Exhibition send*/
        datePhotoEventBus = datePhoto.message
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
            //here get the image from Exhibition
            when {
                requestCode == REQUEST_GALLERY_TIMELINE && resultCode == Activity.RESULT_OK -> {
                    data?.data?.let { photo ->
                        context?.let { context ->
                            datePhotoEventBus?.let { date ->
                                viewModel.createPushPhoto(
                                    context,
                                    date,
                                    photo
                                )
                            }
                        }
                    }
                }
                requestCode == REQUEST_IMAGE_CAPTURE_TIMELINE && resultCode == Activity.RESULT_OK -> {
                    val imageBitmap =
                        data?.extras?.get(ContactsContract.Intents.Insert.DATA) as Bitmap
                    val bytes = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    val path: String = MediaStore.Images.Media.insertImage(
                        context?.contentResolver,
                        imageBitmap,
                        getString(R.string.change_image_user),
                        null
                    )
                    context?.let { context ->
                        datePhotoEventBus?.let { date ->
                            viewModel.createPushPhoto(
                                context,
                                date,
                                Uri.parse(path)
                            )
                        }
                    }
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
        EventBus.getDefault().unregister(this)
        binding
        super.onDestroy()
    }
}