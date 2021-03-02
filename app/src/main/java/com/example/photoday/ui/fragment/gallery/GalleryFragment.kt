package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.*
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.GalleryAdapter
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.firebasePhotos.FirebasePhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components

class GalleryFragment : BaseFragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel(BaseRepositoryPhoto) }

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

    override fun onStart() {
        super.onStart()
        context?.let { context -> viewModel.createPullPhotos(context) }
    }

    private fun init() {
        statusBarNavigation()
        initObservers()
    }

    private fun initObservers() {
        viewModel.uiStateFlow.asLiveData().observe(viewLifecycleOwner) { imagesList ->
            binding.recycleViewListGallery.layoutManager = LinearLayoutManager(context)
            binding.recycleViewListGallery.adapter = GalleryAdapter(imagesList) { itemPhoto ->
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
        super.onDestroy()
        _binding = null
    }
}