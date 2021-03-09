package com.example.photoday.navigation

import androidx.navigation.NavController
import com.example.photoday.ui.fragment.configuration.ConfigurationFragmentDirections
import com.example.photoday.ui.fragment.gallery.GalleryFragmentDirections
import com.example.photoday.ui.fragment.login.LoginFragmentDirections
import com.example.photoday.ui.fragment.register.RegisterFragmentDirections
import com.example.photoday.ui.fragment.splash.SplashGoodbyeFragmentDirections
import com.example.photoday.ui.fragment.splash.SplashIntroFragmentDirections
import com.example.photoday.ui.fragment.splash.SplashLoginDirections
import com.example.photoday.ui.fragment.timeline.TimelineFragmentDirections

object Navigation {

    fun navFragmentLoginToSplashLogin(navFragment: NavController) {
        /*Navigation between fragments Directions Login and Splash Login*/
        val direction = LoginFragmentDirections.actionLoginFragmentToSplashLogin()
        navFragment.navigate(direction)
    }

    fun navFragmentLoginToTimeline(navFragment: NavController) {
        /*Navigation between fragments Directions Login and Timeline*/
        val direction = LoginFragmentDirections.actionLoginFragmentToTimelineFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentLoginToRegister(navFragment: NavController) {
        /*Navigation between fragments Directions Login and Register*/
        val direction = LoginFragmentDirections.actionLoginFragmentToRegister()
        navFragment.navigate(direction)
    }

    fun navFragmentConfigurationToSplashGoodbye(navFragment: NavController) {
        val direction =
            ConfigurationFragmentDirections.actionConfigurationFragmentToSplashGoodbyeFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentSplashGoodbyeToLogin(navFragment: NavController){
        /*Navigation between fragments Directions*/
        val direction =
            SplashGoodbyeFragmentDirections.actionSplashGoodbyeFragmentToLoginFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentSplashIntroToLogin(navFragment: NavController){
        val direction =
            SplashIntroFragmentDirections.actionSplashIntroFragmentToLoginFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentSplashLoginToTimeline(navFragment: NavController){
        val direction =
            SplashLoginDirections.actionSplashLoginToTimelineFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentRegisterToLogin(navFragment: NavController){
        val direction =
            RegisterFragmentDirections.actionRegisterToLoginFragment()
        navFragment.navigate(direction)
    }

    fun navFragmentTimelineToFullScreen(navFragment: NavController, itemPhoto: String){
        val direction =
            TimelineFragmentDirections.actionNavTimelineFragmentToFullscreenFragment(itemPhoto)
        navFragment.navigate(direction)
    }

    fun navFragmentGalleryToFullScreen(navFragment: NavController, itemPhoto: String){
        val direction =
            GalleryFragmentDirections.actionNavGalleryFragmentToFullscreenFragment(itemPhoto)
        navFragment.navigate(direction)
    }
}