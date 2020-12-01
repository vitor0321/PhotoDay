package com.example.photoday.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SendDataToActivityInterface {

    /*é necessário indicar a o Host, quando estamos trabalhando na activity*/
    private val controlNavigation by lazy { findNavController(R.id.main_activity_nav_host) }
    private val viewModelState by lazy { ViewModelInjector.providerStateAppViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        controlNavigation
            .addOnDestinationChangedListener { controller, destination, arguments ->
                /*aletar o titulo da fragment conforme está no Label do nav_graph*/
                title = destination.label
            }
        /*vai controlar toda a nevegação do botton Navegation*/
        main_activity_nav_bottom.setupWithNavController(controlNavigation)
    }

    override fun sendStateComponents(stateComponents: Components) {
        viewModelState.state(stateComponents, supportActionBar, main_activity_nav_bottom)
    }
}
