package com.example.photoday.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.databinding.ActivityMainBinding
import com.example.photoday.ui.stateBarNavigation.Components
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        statusBarNavigation()
        initButton()
    }

    private fun statusBarNavigation() {
        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.main_activity_nav_host) as NavHostFragment
            val navController: NavController = navHostFragment.navController
            // all components start with HIDE and then each fragment decides what appears or not
            supportActionBar?.hide()
            bottomAppBar.visibility = View.INVISIBLE
            buttonFabAdd.visibility = View.INVISIBLE
            bottomNavMainActivity.visibility = View.INVISIBLE
            //background menu navigation
            bottomNavMainActivity.background = null
            /* control all bottom navigation navigation */
            bottomNavMainActivity.setupWithNavController(navController)
            navController
                .addOnDestinationChangedListener { controller, destination, arguments ->
                    /* change the fragment title as it is in the nav_graph Label */
                    title = null
                }
        }
    }

    private fun initButton() {
        binding.apply {
            buttonFabAdd.setOnClickListener { datePicker() }
        }
    }

    fun statusAppBarNavigation(stateComponents: Components) {
        binding.apply {
            when {
                /* here will activate or not the actionBar */
                stateComponents.appBar -> supportActionBar?.show()
                !stateComponents.appBar -> supportActionBar?.hide()
            }
            when {
                /*here will activate or not the Bottom navegation*/
                stateComponents.bottomNavigation -> {
                    bottomNavMainActivity.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                    buttonFabAdd.visibility = View.VISIBLE
                }
                !stateComponents.bottomNavigation -> {
                    bottomNavMainActivity.visibility = View.INVISIBLE
                    bottomAppBar.visibility = View.INVISIBLE
                    buttonFabAdd.visibility = View.INVISIBLE
                }
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
            R.style.DatePickerDialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
            },
            year,
            month,
            day
        ).show()
    }
}
