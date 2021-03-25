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
import com.example.photoday.constants.toast.Utils.toast
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.adapter.GalleryAdapter
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryFragment : BaseFragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val controlNavigation by lazy { findNavController() }

    private val viewModel by lazy {
        val baseRepositoryPhoto = BaseRepositoryPhoto()
        ViewModelInjector.providerGalleryViewModel(baseRepositoryPhoto)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        CoroutineScope(Dispatchers.Main).launch {
            init()
        }
        return binding.root
    }

    private fun init() {
        viewFlipperControl(CHILD_FIRST, PROGRESS_BAR_VISIBLE)
        statusBarNavigation()
        initStateFlowObserve()
    }

    private fun initStateFlowObserve() {
        viewModel.createPullPhotos().observe(viewLifecycleOwner, { resourceList ->
            resourceList.data?.let { listPhoto ->
                initRecycleView(listPhoto)
            }
            when {
                resourceList.error != null -> toast(resourceList.error)
            }
        })
    }

    private fun initRecycleView(listPhoto: List<ItemPhoto>) {
        val spanCount = SPAN_COUNT
        var layoutManager = GridLayoutManager(context, spanCount)
        CoroutineScope(Dispatchers.Main).launch {
            binding.recycleViewListGallery.run {
                layoutManager = layoutManager
                adapter = GalleryAdapter(listPhoto) { itemPhoto ->
                    Navigation.navFragmentGalleryToFullScreen(controlNavigation,
                        itemPhoto.photo)
                }
            }
        }.isCompleted.apply {
            viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)
        }
    }


    private fun viewFlipperControl(child: Int, visible: Boolean) {
        when {
            child == CHILD_FIRST && visible == PROGRESS_BAR_VISIBLE -> {
                binding.run {
                    viewFlipperGallery.displayedChild = CHILD_FIRST
                    progressFlowGallery.isVisible = PROGRESS_BAR_VISIBLE
                }
            }
            child == CHILD_SECOND && visible == PROGRESS_BAR_INVISIBLE -> {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.run {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}