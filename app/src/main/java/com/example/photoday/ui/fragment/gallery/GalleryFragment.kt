package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.FRAG_GALLERY
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.ui.adapter.GalleryListAdapter
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector

class GalleryFragment : BaseFragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding get() = _binding!!
    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
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
        viewModel.getPhotos()
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        binding.apply {
            viewModel.photosLiveData.observe(viewLifecycleOwner, Observer {
                recycleViewListGallery.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = GalleryListAdapter(it)
                }
            })
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(FRAG_GALLERY)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}