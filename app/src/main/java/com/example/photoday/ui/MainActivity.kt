package com.example.photoday.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface
import com.example.photoday.ui.fragment.calendar.CalendarFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SendDataToActivityInterface {

    /*é necessário indicar a o Host, quando estamos trabalhando na activity*/
    private val controlNavigation by lazy { findNavController(R.id.main_activity_nav_host) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //background menu navigation
        main_activity_nav_bottom.background = null
        init()
    }

    private fun init() {
        fab_bottom_add.setOnClickListener {
            val calendarFragment = CalendarFragment()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_activity_nav_host, calendarFragment)
                addToBackStack(null)
                commit()
            }
        }

        controlNavigation
            .addOnDestinationChangedListener { controller, destination, arguments ->
                /*aletar o titulo da fragment conforme está no Label do nav_graph*/
                title = destination.label
            }
        /*vai controlar toda a nevegação do botton Navegation*/
        main_activity_nav_bottom.setupWithNavController(controlNavigation)
    }

    override fun sendStateComponents(stateComponents: Components) {
        when {
            /*aqui vai ativar ou não a actionBar*/
            stateComponents.appBar -> supportActionBar?.show()
            !stateComponents.appBar -> supportActionBar?.hide()
        }
        when {
            /*aqui vai ativar ou não o Bottom navegation*/
            stateComponents.bottomNavigation -> {
                main_activity_nav_bottom?.visibility = View.VISIBLE
                fab_bottom_add.show()
                bottom_app_bar.performShow()
            }
            !stateComponents.bottomNavigation -> {
                main_activity_nav_bottom?.visibility = View.GONE
                fab_bottom_add.hide()
                bottom_app_bar.performHide()
            }
        }
    }
}
