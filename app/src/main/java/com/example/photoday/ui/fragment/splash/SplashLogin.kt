package com.example.photoday.ui.fragment.splash

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.SPLASH_TIME_OUT
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface

class SplashLogin : Fragment() {

    private val controlNavigation by lazy { findNavController() }
    private lateinit var sendDataToActivityInterface: SendDataToActivityInterface

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
        sendDataToActivityInterface.sendStateComponents(components)

        //define o tempo que a activity estará ativa atá passar para a outra
        Handler(Looper.getMainLooper()).postDelayed({
            /*navegando entre fragment usando o Directions*/
            val direction =
                SplashLoginDirections.actionSplashLoginToTimelineFragment()
            controlNavigation.navigate(direction)
        }, SPLASH_TIME_OUT)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity: Activity = context as Activity
        /*ativando a interface para enviar dados a fragment*/
        sendDataToActivityInterface = activity as SendDataToActivityInterface
    }
}