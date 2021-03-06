package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
<<<<<<< HEAD
import com.example.photoday.ui.adapter.TimelineAdapter
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
=======
>>>>>>> developing
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.item.ItemPhoto
import com.example.photoday.ui.toast.Toast.toast
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TimelineFragment : BaseFragment() {

    private var _viewDataBinding: FragmentTimelineBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val adapterTimeline: TimelineAdapter by inject()

    private val viewModel: TimelineViewModel by viewModel {
        parametersOf(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = FragmentTimelineBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onResume() {
        super.onResume()
        viewFlipperControl(CHILD_FIRST, PROGRESS_BAR_VISIBLE)
    }

    private fun init() {
        statusBarNavigation()
    }

    private fun initObserve() {
        viewModel.createPullPhotos().observe(viewLifecycleOwner) { resourceItem ->
            resourceItem.data?.let(this::upDateAdapter)
            resourceItem.message?.let { messageToast(R.string.error_api) }
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
    private fun initRecycleView(listPhoto: List<ItemPhoto>) {
<<<<<<< HEAD
        binding.run {
            recycleViewListTimeline.layoutManager = LinearLayoutManager(context)
            recycleViewListTimeline.run {
                adapter = TimelineAdapter(listPhoto) { itemPhoto ->
                        navFragmentTimelineToFullScreen(controlNavigation, itemPhoto.photo)
                    }
            }
            CoroutineScope(Dispatchers.IO).launch {
                delay(TIME_DELAY)
                viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)
=======
        CoroutineScope(Dispatchers.Main).launch {
            viewDataBinding.recycleViewListTimeline.run {
                layoutManager = LinearLayoutManager(context)
                adapter= TimelineAdapter(context, listPhoto) { itemPhoto ->
                    viewModel.navFragment(itemPhoto)
                }
<<<<<<< HEAD

>>>>>>> developing
=======
>>>>>>> developing
=======
    private fun initRecycleView(){
=======
    private fun upDateAdapter(listNote: List<ItemPhoto>) {
        adapterTimeline.update(listNote)
        initRecycleView()
    }

    private fun initRecycleView() {
>>>>>>> developing
        viewDataBinding.recycleViewListTimeline.run {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapterTimeline
            adapterTimeline.onItemClickListener = { itemPhoto ->
                viewModel.navFragment(itemPhoto)
>>>>>>> developing
            }
        }
        viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)
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
                viewDataBinding.run {
                    viewFlipperTimeline.displayedChild = CHILD_SECOND
                    progressFlowTimeline.isVisible = PROGRESS_BAR_INVISIBLE
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
                floatingActionButton = TRUE,
                actionBar = FALSE),
            barColor = R.color.orange_status_bar)
    }

    private fun messageToast(message: Int?) {
        message?.let { messageInt->
            val messageToast = this.getString(messageInt)
            toast(messageToast)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}