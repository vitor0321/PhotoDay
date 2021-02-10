package com.example.photoday.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.SPLASH_TIME_OUT
import com.example.photoday.databinding.FragmentSplashGoodbyeBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.navigation.Navigation.navFragmentSplashGoodbyeToLogin
import com.example.photoday.ui.stateBarNavigation.Components

class SplashGoodbyeFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashGoodbyeBinding
    private val controlNavigation by lazy { findNavController() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashGoodbyeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        statusBarNavigation()

        //define time that the activity is active until it passes to the other
        Handler(Looper.getMainLooper()).postDelayed({
            navFragmentSplashGoodbyeToLogin(controlNavigation)
        }, SPLASH_TIME_OUT)
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(false, Components(FALSE, FALSE), R.color.white_status_bar)
    }

    override fun onDestroy() {
        binding
        super.onDestroy()
    }
}