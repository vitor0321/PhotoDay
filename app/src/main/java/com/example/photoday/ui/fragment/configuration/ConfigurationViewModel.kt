package com.example.photoday.ui.fragment.configuration

import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.fragment.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class ConfigurationViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()

    fun navFragmentLogin(navFragment: NavController) {
        val direction =
            ConfigurationFragmentDirections.actionConfigurationFragmentToSplashGoodbyeFragment()
        navFragment.navigate(direction)
    }

    fun navTimeline(navFragment: NavController) {
        val direction =
            ConfigurationFragmentDirections.actionConfigurationFragmentToTimelineFragment()
        navFragment.navigate(direction)
    }

    fun sentStatusToBase(viewModelBase: BaseViewModel) {
        val components = Components(TRUE, FALSE)
        viewModelBase.stateFragment(components)
    }

    /*set name, emamil e photo do usuário*/
    fun googleSingIn(
        text_view_user_name: AppCompatTextView,
        text_view_user_email: AppCompatTextView
    ) {
        val googleSignIn = auth.currentUser
        when {
            googleSignIn != null -> {
                text_view_user_name.text = googleSignIn.displayName
                text_view_user_email.text = googleSignIn.email
            }
            else -> {
                text_view_user_name.text = R.string.name_user.toString()
                text_view_user_email.text = R.string.email_user.toString()
            }
        }
    }
}