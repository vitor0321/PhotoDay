package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.constants.toast.Utils.toast
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.navigation.Navigation.navFragmentTimelineToFullScreen
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.adapter.TimelineAdapter
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class TimelineFragment : BaseFragment() {

    private var _viewDataBinding: FragmentTimelineBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val controlNavigation by lazy { findNavController() }

    private val viewModel by lazy {
        val baseRepositoryPhoto = BaseRepositoryPhoto()
        ViewModelInjector.providerTimelineViewModel(baseRepositoryPhoto)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = FragmentTimelineBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            init()
        }
    }

    private fun init() {
        viewFlipperControl(CHILD_FIRST, PROGRESS_BAR_VISIBLE)
        statusBarNavigation()
        initObserve()
    }

    private fun initObserve() {
        this.viewModel.createPullPhotos().observe(viewLifecycleOwner) { resourceList ->
            resourceList.data?.let { listPhoto ->
                initRecycleView(listPhoto)
            }
            messageToast(resourceList.error)
        }
    }

    private fun initRecycleView(listPhoto: List<ItemPhoto>) {
        CoroutineScope(Dispatchers.Main).launch {
            viewDataBinding.recycleViewListTimeline.run {
                layoutManager = LinearLayoutManager(context)
                adapter = TimelineAdapter(context, listPhoto) { itemPhoto ->
                    navFragmentTimelineToFullScreen(controlNavigation, itemPhoto.photo)
                }

            }
        }.isCompleted.apply {
            viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)
        }
    }

    private fun viewFlipperControl(child: Int, visible: Boolean) {
        when {
            child == CHILD_FIRST && visible == PROGRESS_BAR_VISIBLE -> {
                viewDataBinding.run {
                    viewFlipperTimeline.displayedChild = CHILD_FIRST
                    progressFlowTimeline.isVisible = PROGRESS_BAR_VISIBLE
                }
            }
            child == CHILD_SECOND && visible == PROGRESS_BAR_INVISIBLE -> {
                CoroutineScope(Dispatchers.Main).launch {
                    viewDataBinding.run {
                        viewFlipperTimeline.displayedChild = CHILD_SECOND
                        progressFlowTimeline.isVisible = PROGRESS_BAR_INVISIBLE
                    }
                }
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = TRUE,
                bottomNavigation = TRUE,
                floatingActionButton = TRUE),
            barColor = R.color.orange_status_bar)
    }

    private fun messageToast(message: String?) {
        message?.let { message -> toast(message) }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        this._viewDataBinding = null
    }
}