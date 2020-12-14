package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoday.R
import com.example.photoday.constants.TRUE
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.fragment.base.BaseFragment

class TimelineFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerTimelineViewModel() }
    private val viewModelBase by lazy { ViewModelInjector.providerBaseViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        val components = Components(TRUE, TRUE)
        viewModelBase.stateFragmentBottom(components)
    }
}