package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.*
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.TimelineAdapter
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class TimelineFragment : BaseFragment() {

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelInjector.providerTimelineViewModel(BaseRepositoryPhoto) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
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
        CoroutineScope(Dispatchers.Main).launch {
            context?.let { context -> viewModel.createPullPhotos(context) }
        }
    }

    private fun init() {
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiStateFlow.asLiveData().observe(viewLifecycleOwner) { imagesList ->
                binding.recycleViewListTimeline.layoutManager = LinearLayoutManager(context)
                imagesList
                binding.recycleViewListTimeline.adapter = TimelineAdapter(imagesList) { itemPhoto ->
                    /**
                     * quando clicar na photo, vai fazer o que ?
                     */
                }
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(true, Components(TRUE, TRUE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        _binding = null
    }
}