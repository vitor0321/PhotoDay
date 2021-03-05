package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.GalleryAdapter
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GalleryFragment : BaseFragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel(BaseRepositoryPhoto) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            init()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_gallery, menu)
    }

    override fun onStart() {
        super.onStart()
        viewFlipperControl(CHILD_FIRST, PROGRESS_BAR_VISIBLE)
        context?.let { context -> viewModel.createPullPhotos(context) }
    }

    private fun init() {
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        viewModel.uiStateFlow.asLiveData().observe(viewLifecycleOwner) { imagesList ->
            val spanCount = SPAN_COUNT
            val layoutManager = GridLayoutManager(context, spanCount)
            binding.run {
                recycleViewListGallery.layoutManager = layoutManager
                recycleViewListGallery.adapter = GalleryAdapter(imagesList) { itemPhoto ->

                }
            }
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
                    delay(DELAY_VIEW_FLIPPER)
                    binding.run {
                        viewFlipperGallery.displayedChild = CHILD_SECOND
                        progressFlowGallery.isVisible = PROGRESS_BAR_INVISIBLE
                    }
                }
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(true, Components(TRUE, TRUE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}