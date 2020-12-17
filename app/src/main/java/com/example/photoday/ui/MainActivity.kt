package com.example.photoday.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.stateAppBarBottonNavigation.Components
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    /*é necessário indicar a o Host, quando estamos trabalhando na activity*/
    private val controlNavigation by lazy { findNavController(R.id.main_activity_nav_host) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //background menu navigation
        main_activity_nav_bottom.background = null
        init()
    }

    fun statusAppBarNavigation(stateComponents: Components) {
        when {
            /*aqui vai ativar ou não a actionBar*/
            stateComponents.appBar -> supportActionBar?.show()
            !stateComponents.appBar -> supportActionBar?.hide()
        }
        when {
            /*aqui vai ativar ou não o Bottom navegation*/
            stateComponents.bottomNavigation -> {
                main_activity_nav_bottom?.visibility = View.VISIBLE
                bottom_app_bar?.visibility = View.VISIBLE
                fab_bottom_add?.visibility = View.VISIBLE
            }
            !stateComponents.bottomNavigation -> {
                main_activity_nav_bottom?.visibility = View.INVISIBLE
                bottom_app_bar?.visibility = View.INVISIBLE
                fab_bottom_add?.visibility = View.INVISIBLE
            }
        }
    }

    private fun init() {
        fab_bottom_add.setOnClickListener { datePicker()}

        controlNavigation
            .addOnDestinationChangedListener { controller, destination, arguments ->
                /*aletar o titulo da fragment conforme está no Label do nav_graph*/
                title = destination.label
            }
        /*vai controlar toda a nevegação do botton Navegation*/
        main_activity_nav_bottom.setupWithNavController(controlNavigation)
    }

    private fun datePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
        }, year, month, day).show()
    }
}
