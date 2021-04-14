package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.ui.adapter.GalleryAdapter
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GalleryFragment : BaseFragment() {

    private var _viewDataBinding: FragmentGalleryBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: GalleryViewModel by viewModel{
        parametersOf(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        this._viewDataBinding = FragmentGalleryBinding.inflate(inflater, container, false)
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
        initStateFlowObserve()
    }

    private fun initStateFlowObserve() {
        this.viewModel.createPullPhotos().observe(viewLifecycleOwner, { resourceList ->
            resourceList.data?.let { listPhoto ->
                initRecycleView(listPhoto)
            }
            messageToast(resourceList.error)
        })
    }

    private fun initRecycleView(listPhoto: List<ItemPhoto>) {
        val spanCount = SPAN_COUNT
        val layoutManagerAdapter = GridLayoutManager(context, spanCount)
        CoroutineScope(Dispatchers.Main).launch {
            viewDataBinding.recycleViewListGallery.run {
                layoutManager = layoutManagerAdapter
                adapter = GalleryAdapter(context, listPhoto) { itemPhoto ->
                    viewModel.navFragment(itemPhoto.photo)
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
                    viewFlipperGallery.displayedChild = CHILD_FIRST
                    progressFlowGallery.isVisible = PROGRESS_BAR_VISIBLE
                }
            }
            child == CHILD_SECOND && visible == PROGRESS_BAR_INVISIBLE -> {
                CoroutineScope(Dispatchers.Main).launch {
                    viewDataBinding.run {
                        viewFlipperGallery.displayedChild = CHILD_SECOND
                        progressFlowGallery.isVisible = PROGRESS_BAR_INVISIBLE
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
        this._viewDataBinding = null
    }
}