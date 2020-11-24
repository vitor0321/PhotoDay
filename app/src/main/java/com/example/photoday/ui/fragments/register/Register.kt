package com.example.fragmenttest.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fragmenttest.activity.state.Components
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register_user.*

class Register : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerRegisterViewModel() }
    private val stateViewModel by lazy { ViewModelInjector.providerStateAppViewModel() }
    private val controlNavigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateViewModel.hasComponents = Components(false,false)
        init()
    }

    private fun init() {
        register_user_button.setOnClickListener {
            viewModel.navFragment(controlNavigation)
        }
    }
}