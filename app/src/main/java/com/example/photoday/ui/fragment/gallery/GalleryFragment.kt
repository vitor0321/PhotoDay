package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.GalleryAdapter
import com.example.photoday.adapter.modelAdapter.ItemPhoto
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
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        viewModel.data.observe(viewLifecycleOwner, { photoList ->
            getPhotoList(photoList)
        })
    }

    private fun getPhotoList(photosList: List<ItemPhoto>) = photosList.let { photosList ->
        binding.recycleViewListGallery.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = GalleryAdapter(photosList) { itemPhoto ->
                /**
                 * quando clicar na photo, vai fazer o que ?
                 */
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(true, Components(TRUE, TRUE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        binding
        super.onDestroy()
    }
}