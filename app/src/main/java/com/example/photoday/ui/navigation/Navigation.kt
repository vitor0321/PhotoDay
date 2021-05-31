package com.example.photoday.ui.navigation

import androidx.navigation.NavController
import com.example.photoday.ui.fragment.configuration.ConfigurationFragmentDirections
import com.example.photoday.ui.fragment.gallery.GalleryFragmentDirections
import com.example.photoday.ui.fragment.login.LoginFragmentDirections
import com.example.photoday.ui.fragment.note.NoteFragmentDirections
import com.example.photoday.ui.fragment.register.RegisterFragmentDirections
import com.example.photoday.ui.fragment.splash.SplashGoodbyeFragmentDirections
import com.example.photoday.ui.fragment.splash.SplashIntroFragmentDirections
import com.example.photoday.ui.fragment.splash.SplashLoginFragmentDirections
import com.example.photoday.ui.fragment.timeline.TimelineFragmentDirections
import com.example.photoday.ui.model.item.ItemNote
import com.example.photoday.ui.model.item.ItemPhoto

object Navigation {

    fun navFragmentLoginToSplashLogin(navFragment: NavController) {
        LoginFragmentDirections.actionLoginFragmentToSplashLogin()
            .let(navFragment::navigate)
    }

    fun navFragmentLoginToTimeline(navFragment: NavController) {
        LoginFragmentDirections.actionLoginFragmentToTimelineFragment()
            .let(navFragment::navigate)
    }

    fun navFragmentLoginToRegister(navFragment: NavController) {
        LoginFragmentDirections.actionLoginFragmentToRegister()
            .let(navFragment::navigate)
    }

    fun navFragmentConfigurationToSplashGoodbye(navFragment: NavController) {
        ConfigurationFragmentDirections.actionConfigurationFragmentToSplashGoodbyeFragment()
            .let(navFragment::navigate)
    }

    fun navFragmentSplashGoodbyeToLogin(navFragment: NavController){
        SplashGoodbyeFragmentDirections.actionSplashGoodbyeFragmentToLoginFragment()
            .let(navFragment::navigate)
    }

    fun navFragmentSplashIntroToLogin(navFragment: NavController){
        SplashIntroFragmentDirections.actionSplashIntroFragmentToLoginFragment()
            .let(navFragment::navigate)
    }

    fun navFragmentSplashLoginToTimeline(navFragment: NavController) {
        SplashLoginFragmentDirections.actionSplashLoginToTimelineFragment()
            .let(navFragment::navigate)
    }

    fun navFragmentRegisterToLogin(navFragment: NavController) {
        RegisterFragmentDirections.actionRegisterToLoginFragment()
            .let(navFragment::navigate)
    }

    fun navFragmentTimelineToFullScreenPhoto(
        navFragment: NavController,
        itemPhoto: ItemPhoto
    ) {
        TimelineFragmentDirections.actionNavTimelineFragmentToFullScreenPhotoFragment(
            itemDate = itemPhoto.date,
            itemPhoto = itemPhoto.photo
        )
            .let(navFragment::navigate)
    }


    fun navFragmentGalleryToFullScreenPhoto(
        navFragment: NavController,
        itemPhoto: ItemPhoto
    ) {
        GalleryFragmentDirections.actionNavGalleryFragmentToFullScreenPhotoFragment(
            itemDate = itemPhoto.date,
            itemPhoto = itemPhoto.photo
        )
            .let(navFragment::navigate)
    }

    fun navFragmentNoteToFullScreenNote(navFragment: NavController, itemNota: ItemNote) {
        NoteFragmentDirections.actionNavNoteFragmentToFullScreenNoteFragment(
            itemDate = itemNota.date,
            itemTitle = itemNota.title,
            itemNote = itemNota.note
        )
            .let(navFragment::navigate)
    }
}