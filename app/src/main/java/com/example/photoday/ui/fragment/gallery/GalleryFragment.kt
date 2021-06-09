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
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.toast.Toast.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GalleryFragment : BaseFragment() {

    private var _viewDataBinding: FragmentGalleryBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val adapterGallery: GalleryAdapter by inject()
    private val viewModel: GalleryViewModel by viewModel {
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
        init()
    }

    override fun onResume() {
        super.onResume()
        viewFlipperControl(CHILD_FIRST, PROGRESS_BAR_VISIBLE)
        initObserve()
    }

    private fun init() {
        statusBarNavigation()
    }

    private fun initObserve() {
        viewModel.createPullPhotos().observe(viewLifecycleOwner, { resourceList ->
            resourceList.data?.let { listPhoto ->
                adapterGallery.update(listPhoto)
                initRecycleView()
            }
            when (resourceList.message) {
                FALSE -> messageToast(R.string.error_api)
            }
        })
    }

    private fun initRecycleView() {
        val spanCount = SPAN_COUNT
        val layoutManagerAdapter = GridLayoutManager(context, spanCount)
        viewDataBinding.recycleViewListGallery.run {
            layoutManager = layoutManagerAdapter
            this.adapter = adapterGallery
            adapterGallery.onItemClickListener = { itemPhoto ->
                viewModel.navFragment(itemPhoto)
            }
        }
        viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)
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
                floatingActionButton = TRUE,
                actionBar = FALSE
            ),
            barColor = R.color.orange_status_bar
        )
    }

    private fun messageToast(message: Int?) {
        message?.let { messageInt ->
            val messageToast = this.getString(messageInt)
            toast(messageToast)
        }
    }

    override fun onStop() {
        super.onStop()
        this._viewDataBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}