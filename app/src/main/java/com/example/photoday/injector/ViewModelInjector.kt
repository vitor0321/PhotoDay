package com.example.photoday.injector

import com.example.photoday.repository.sharedPreferences.LoginRepositoryShared
import com.example.photoday.ui.fragment.base.BaseViewModel
import com.example.photoday.ui.fragment.base.BaseViewModelFactory
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModel
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModelFactory
import com.example.photoday.ui.fragment.gallery.GalleryViewModel
import com.example.photoday.ui.fragment.gallery.GalleryViewModelFactory
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.login.LoginViewModelFactory
import com.example.photoday.ui.fragment.login.Logout
import com.example.photoday.ui.fragment.register.RegisterViewModel
import com.example.photoday.ui.fragment.register.RegisterViewModelFactory
import com.example.photoday.ui.fragment.timeline.TimelineViewModel
import com.example.photoday.ui.fragment.timeline.TimelineViewModelFatory

object ViewModelInjector {

    fun providerConfigurationViewModel(): ConfigurationViewModel {
        return ConfigurationViewModelFactory()
            .create(ConfigurationViewModel::class.java)
    }

    fun providerRegisterViewModel(): RegisterViewModel {
        return RegisterViewModelFactory()
            .create(RegisterViewModel::class.java)
    }

    fun providerLoginViewModel(): LoginViewModel {
        return LoginViewModelFactory()
            .create(LoginViewModel::class.java)
    }

    fun providerBaseViewModel(): BaseViewModel {
        return BaseViewModelFactory()
            .create(BaseViewModel::class.java)
    }

    fun providerGalleryViewModel(): GalleryViewModel {
        return GalleryViewModelFactory()
            .create(GalleryViewModel::class.java)
    }

    fun providerTimelineViewModel(): TimelineViewModel {
        return TimelineViewModelFatory()
            .create(TimelineViewModel::class.java)
    }
}