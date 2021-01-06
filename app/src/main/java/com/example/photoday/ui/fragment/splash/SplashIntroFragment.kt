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
import com.example.photoday.databinding.FragmentSplashIntroBinding
import com.example.photoday.navigation.Navigation.navFragmentSplashIntroToLogin
import com.example.photoday.stateBarNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment

class SplashIntroFragment : BaseFragment() {

    private val controlNavigation by lazy { findNavController() }
    private lateinit var binding: FragmentSplashIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashIntroBinding.bind(view)
        init()
    }

    private fun init() {
        statusBarNavigation()

        ///define time that the activity is active until it passes to the other
        Handler(Looper.getMainLooper()).postDelayed({
            /*Navigation between fragments Directions*/
            navFragmentSplashIntroToLogin(controlNavigation)
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
}