package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.TimelineAdapter
import com.example.photoday.constants.*
import com.example.photoday.constants.Utils.toast
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class TimelineFragment : BaseFragment() {

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!

    private val controlNavigation by lazy { findNavController() }
    private val baseRepositoryPhoto: BaseRepositoryPhoto = BaseRepositoryPhoto()

    private val viewModel by lazy { ViewModelInjector.providerTimelineViewModel(baseRepositoryPhoto) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            init()
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            viewFlipperControl(CHILD_FIRST,PROGRESS_BAR_VISIBLE)
            context?.let { context -> viewModel.createPullPhotos(context) }
        }
    }

    private fun init() {
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        viewModel.uiStateFlow.asLiveData().observe(viewLifecycleOwner) { imagesList ->
            binding.run {
                recycleViewListTimeline.layoutManager = LinearLayoutManager(context)
                recycleViewListTimeline.adapter = TimelineAdapter(imagesList) { itemPhoto ->
                    Navigation.navFragmentTimelineToFullScreen(controlNavigation, itemPhoto.photo)
                }
            }
        }

        viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)

        viewModel.uiStateFlowError.asLiveData().observe(viewLifecycleOwner) { message ->
            context?.let { context -> toast(context, message) }
        }
    }

    private fun viewFlipperControl(child : Int, visible : Boolean){
        when{
            child == CHILD_FIRST && visible == PROGRESS_BAR_VISIBLE ->{
                binding.run{
                    viewFlipperTimeline.displayedChild = CHILD_FIRST
                    progressFlowTimeline.isVisible = PROGRESS_BAR_VISIBLE
                }
            }
            child == CHILD_SECOND && visible == PROGRESS_BAR_INVISIBLE ->{
                CoroutineScope(Dispatchers.Main).launch {
                    delay(DELAY_VIEW_FLIPPER)
                    binding.run {
                        viewFlipperTimeline.displayedChild = CHILD_SECOND
                        progressFlowTimeline.isVisible = PROGRESS_BAR_INVISIBLE
                    }
                }
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(FALSE_MENU, Components(FALSE_MENU, TRUE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        _binding = null
    }
}