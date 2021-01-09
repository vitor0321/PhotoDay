package com.example.photoday.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.SPLASH_TIME_OUT
import com.example.photoday.databinding.FragmentSplashLoginBinding
import com.example.photoday.ui.navigation.Navigation.navFragmentSplashLoginToTimeline
import com.example.photoday.ui.stateBarNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment

class SplashLogin : BaseFragment() {

    private var _binding : FragmentSplashLoginBinding? = null
    private val binding: FragmentSplashLoginBinding get() = _binding!!
    private val controlNavigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashLoginBinding.inflate(inflater, container, false)
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
            /*Navigation between fragments Directions*/
            navFragmentSplashLoginToTimeline(controlNavigation)
        }, SPLASH_TIME_OUT)
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color of statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(
            requireContext(), R.color.white
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}