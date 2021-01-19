package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.TRUE
import com.example.photoday.data.modelAdapter.GalleryAdapter
import com.example.photoday.data.modelAdapter.TimelineAdapter
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.ui.PhotoDayActivity
import com.example.photoday.ui.adapter.GalleryListAdapter
import com.example.photoday.ui.adapter.TimelineListAdapter
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components

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

    private fun init(){
        statusBarNavigation()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.apply {
            recycleViewListGallery.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = GalleryListAdapter(getPhotos())
            }
        }
    }

    //isso é profissório para teste, pois vamos pegar essa lista do nosso BD
    private fun getPhotos(): List<GalleryAdapter>{
        return listOf(
                GalleryAdapter(R.drawable.ic_item_photo),
                GalleryAdapter(R.drawable.ic_item_photo),
                GalleryAdapter(R.drawable.ic_item_photo),
                GalleryAdapter(R.drawable.ic_item_photo),
                GalleryAdapter(R.drawable.ic_item_photo),
                GalleryAdapter(R.drawable.ic_item_photo),
                GalleryAdapter(R.drawable.ic_item_photo)
        )
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(TRUE, TRUE)
        val mainActivity = requireActivity() as PhotoDayActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}