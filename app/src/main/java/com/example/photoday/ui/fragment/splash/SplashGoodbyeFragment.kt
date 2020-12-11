package com.example.photoday.ui.fragment.splash

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.SPLASH_TIME_OUT
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface
import com.example.photoday.ui.fragment.splash.SplashGoodbyeFragmentDirections

class SplashGoodbyeFragment : Fragment() {

    private val controlNavigation by lazy { findNavController() }
    private lateinit var sendDataToActivityInterface: SendDataToActivityInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_goodbye, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        val components = Components(FALSE, FALSE)
        sendDataToActivityInterface.sendStateComponents(components)

        /*mudar a cor do statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white
        )

        //define o tempo que a activity estará ativa atá passar para a outra
        Handler(Looper.getMainLooper()).postDelayed({
            /*navegando entre fragment usando o Directions*/
            val direction =
                SplashGoodbyeFragmentDirections.actionSplashGoodbyeFragmentToLoginFragment()
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