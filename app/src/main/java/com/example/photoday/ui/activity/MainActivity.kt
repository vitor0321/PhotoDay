package com.example.photoday.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

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

                viewModelState.components.observe(this, Observer {
                    /*se o It for verdadeiro ou falso mostre ou não a Actionbar e também
                    * o Bottom Navegation*/
                    it?.let { hasComponents ->
                        when {
                            /*aqui vai ativar ou não a actionBar*/
                            hasComponents.appBar -> supportActionBar?.show()
                            !hasComponents.appBar -> supportActionBar?.hide()
                        }
                        when {
                            /*aqui vai ativar ou não o Bottom navegation*/
                            hasComponents.bottomNavigation ->
                                main_activity_nav_bottom.visibility = View.VISIBLE
                            !hasComponents.bottomNavigation ->
                                main_activity_nav_bottom.visibility = View.GONE
                        }
                    }
                })
            }
        /*vai controlar toda a nevegação do botton Navegation*/
        main_activity_nav_bottom.setupWithNavController(controlNavigation)
    }
}