package com.example.photoday.stateAppBarBottonNavigation

import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class StateAppViewModel : ViewModel() {

    fun state(
        components: Components,
        supportActionBar: ActionBar?,
        main_activity_nav_bottom: BottomNavigationView?
    ) {
        when {
            /*aqui vai ativar ou não a actionBar*/
            components.appBar -> supportActionBar?.show()
            !components.appBar -> supportActionBar?.hide()
        }
        when {
            /*aqui vai ativar ou não o Bottom navegation*/
            components.bottomNavigation ->
                main_activity_nav_bottom?.visibility = View.VISIBLE
            !components.bottomNavigation ->
                main_activity_nav_bottom?.visibility = View.GONE
        }
    }
}


