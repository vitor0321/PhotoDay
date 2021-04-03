package com.example.photoday.di

import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.activity.PhotoDayViewModel
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModel
import com.example.photoday.ui.fragment.gallery.GalleryViewModel
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.register.RegisterViewModel
import com.example.photoday.ui.fragment.timeline.TimelineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulePhotoDay = module(override = true) {
    single<BaseRepositoryPhoto> {
        BaseRepositoryPhoto()
    }

    viewModel {
        PhotoDayViewModel(
            repository = get()
        )
    }
}

val moduleConfiguration = module(override = true) {
    single<BaseRepositoryUser> {
        BaseRepositoryUser()
    }

    viewModel { (navFragment: NavController) ->
        ConfigurationViewModel(
            repository = get(),
            navFragment = navFragment
        )
    }
}

val moduleGallery = module(override = true) {
    single<BaseRepositoryPhoto> {
        BaseRepositoryPhoto()
    }

    viewModel { (navFragment: NavController) ->
        GalleryViewModel(
            repository = get(),
            navFragment = navFragment
        )
    }
}

val moduloLogin = module(override = true) {
    single<BaseRepositoryUser> {
        BaseRepositoryUser()
    }

    viewModel { (navFragment: NavController) ->
        LoginViewModel(
            repository = get(),
            navFragment = navFragment
        )
    }
}

val moduloRegister = module(override = true) {
    single<BaseRepositoryUser> {
        BaseRepositoryUser()
    }

    viewModel { (navFragment: NavController) ->
        RegisterViewModel(
            repository = get(),
            navFragment = navFragment
        )
    }
}

val moduloTimeline = module(override = true) {
    single<BaseRepositoryPhoto> {
        BaseRepositoryPhoto()
    }

    viewModel { (navFragment: NavController) ->
        TimelineViewModel(
            repository = get(),
            navFragment = navFragment
        )
    }
}