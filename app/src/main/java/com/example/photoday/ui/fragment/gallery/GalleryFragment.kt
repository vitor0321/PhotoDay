package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.GalleryListAdapter
import com.example.photoday.adapter.TimelineListAdapter
import com.example.photoday.adapter.modelAdapter.GalleryAdapter
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_timeline.*

class GalleryFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel() }
    private lateinit var galleryAdapter: GalleryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initRecyclerView()
    }

    private fun init() {
        /*Enviando o status do AppBar e do Bottom Navigation para a Activity*/
        val statusAppBarNavigation = Components(TRUE, TRUE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)
    }

    private fun initRecyclerView() {
        recycle_view_list_gallery.apply {
            layoutManager = LinearLayoutManager(context)
            galleryAdapter = GalleryListAdapter()
            adapter = galleryAdapter
        }
    }
}