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
import com.example.photoday.adapter.TimelineAdapter
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.constants.REQUEST_GALLERY_TIMELINE
import com.example.photoday.constants.REQUEST_IMAGE_CAPTURE_TIMELINE
import com.example.photoday.constants.TRUE
import com.example.photoday.constants.Utils
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.eventBus.MessageEvent
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
        context?.let { context -> viewModel.createPullPhotos(null, context, binding) }
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