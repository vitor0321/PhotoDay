package com.example.photoday.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.constants.GALLERY_TYPE
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.permission.CheckVersionPermission.galleryPermission
import com.example.photoday.stateAppBarBottonNavigation.Components
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val controlNavigation by lazy { findNavController(R.id.main_activity_nav_host) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        //background menu navigation
        main_activity_nav_bottom.background = null

        // all components start with HIDE and then each fragment decides what appears or not
        supportActionBar?.hide()
        main_activity_nav_bottom?.visibility = View.INVISIBLE
        bottom_app_bar?.visibility = View.INVISIBLE
        fab_bottom_add?.visibility = View.INVISIBLE

        fab_bottom_add.setOnClickListener {
            datePicker()
        }

        controlNavigation
            .addOnDestinationChangedListener { controller, destination, arguments ->
                /* change the fragment title as it is in the nav_graph Label */
                title = destination.label
            }
        /* control all bottom navigation navigation */
        main_activity_nav_bottom.setupWithNavController(controlNavigation)
    }

    fun statusAppBarNavigation(stateComponents: Components) {
        when {
            /* here will activate or not the actionBar */
            stateComponents.appBar -> supportActionBar?.show()
            !stateComponents.appBar -> supportActionBar?.hide()
        }
        when {
            /*here will activate or not the Bottom navegation*/
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

    private fun datePicker() {
        /*inflater datePicker*/
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            R.style.MyDatePickerDialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
            },
            year,
            month,
            day
        ).show()
    }
}
