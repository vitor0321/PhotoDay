package com.example.photoday.ui.fragments.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmenttest.activity.state.Components
import com.example.photoday.R
import com.example.photoday.ui.fragments.base.BaseFragment
import com.example.photoday.injector.ViewModelInjector

class GalleryFragment : BaseFragment(){

    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel() }
    private val stateViewModel  by lazy { ViewModelInjector.providerStateAppViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateViewModel.hasComponents = Components(true,true)
    }
}