package com.example.photoday.ui.fragment.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.ui.fragment.base.BaseFragment

class GalleryFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerGalleryViewModel() }
    private val viewModelBase by lazy { ViewModelInjector.providerBaseViewModel()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity()
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.sentStatusToBase(viewModelBase)
    }
}