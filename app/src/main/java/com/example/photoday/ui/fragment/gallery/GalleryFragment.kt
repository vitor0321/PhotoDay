package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.GalleryListAdapter
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components
import kotlinx.android.synthetic.main.fragment_configuration.view.*
import kotlinx.android.synthetic.main.item_gallery_fragment.view.*

class GalleryFragment : BaseFragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding get() = _binding!!
    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel() }
    private lateinit var galleryAdapter: GalleryListAdapter

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

    private fun init(){
        statusBarNavigation()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.apply {

            recycleViewListGallery.apply {
                layoutManager = LinearLayoutManager(context)
                galleryAdapter = GalleryListAdapter()
                adapter = galleryAdapter
            }
        }
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(TRUE, TRUE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}