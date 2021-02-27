package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.*
import com.example.photoday.R
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import org.greenrobot.eventbus.EventBus

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