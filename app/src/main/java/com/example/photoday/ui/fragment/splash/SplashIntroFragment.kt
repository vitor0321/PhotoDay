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
import com.example.photoday.constants.FALSE_MENU
import com.example.photoday.constants.SPLASH_TIME_OUT
import com.example.photoday.databinding.FragmentSplashIntroBinding
import com.example.photoday.ui.navigation.Navigation.navFragmentSplashIntroToLogin
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components

class SplashIntroFragment : BaseFragment() {

    private var _binding: FragmentSplashIntroBinding? = null
    private val binding get() = _binding!!

    private val controlNavigation by lazy { findNavController() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        statusBarNavigation()
        ///define time that the activity is active until it passes to the other
        Handler(Looper.getMainLooper()).postDelayed({
            /*Navigation between fragments Directions*/
            navFragmentSplashIntroToLogin(controlNavigation)
            onDestroy()
        }, SPLASH_TIME_OUT)
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = FALSE,
                bottomNavigation = FALSE,
                floatingActionButton = FALSE,
                actionBar = FALSE),
            barColor = R.color.white_status_bar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}