package com.example.photoday.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.databinding.ActivityMainBinding
import com.example.photoday.stateBarNavigation.Components
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val controlNavigation by lazy { findNavController(R.id.main_activity_nav_host) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        init()
    }

    private fun init() {
        statusBarNavigation()
        initButton()
    }

    private fun statusBarNavigation() {
        binding.run {
            // all components start with HIDE and then each fragment decides what appears or not
            supportActionBar?.hide()
            mainActivityNavBottom.visibility = View.INVISIBLE
            bottomAppBar.visibility = View.INVISIBLE
            fabBottomAdd.visibility = View.INVISIBLE

            //background menu navigation
            mainActivityNavBottom.background = null
            /* control all bottom navigation navigation */
            mainActivityNavBottom.setupWithNavController(controlNavigation)

            controlNavigation
                .addOnDestinationChangedListener { _, _, _ ->
                    /* change the fragment title as it is in the nav_graph Label */
                    title = null
                }
        }
    }

    private fun initButton() {
        binding.run {
            fabBottomAdd.setOnClickListener { datePicker() }
        }
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
