package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.*
import com.example.photoday.R
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components

class GalleryFragment : BaseFragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_gallery, menu)
    }

    private fun init() {
        binding.progressBar.visibility = View.VISIBLE
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        context?.let { context -> viewModel.createPullPhotos(binding, context) }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(true, Components(TRUE, TRUE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        binding
        super.onDestroy()
    }
}