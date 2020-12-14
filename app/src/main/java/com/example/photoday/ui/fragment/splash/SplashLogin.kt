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
import com.example.photoday.constants.TRUE
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.fragment.base.BaseFragment

class SplashLogin : BaseFragment(){

    private val controlNavigation by lazy { findNavController() }
    private val viewModelBase by lazy { ViewModelInjector.providerBaseViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        val components = Components(FALSE, FALSE)
        viewModelBase.stateFragmentBottom(components)

        /*mudar a cor do statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        //define o tempo que a activity estará ativa atá passar para a outra
        Handler(Looper.getMainLooper()).postDelayed({
            /*navegando entre fragment usando o Directions*/
            val direction =
                SplashLoginDirections.actionSplashLoginToTimelineFragment()
            controlNavigation.navigate(direction)
        }, SPLASH_TIME_OUT)
    }
}