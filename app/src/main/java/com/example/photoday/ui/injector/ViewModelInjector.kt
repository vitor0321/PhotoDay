package com.example.photoday.ui.injector

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.photoday.ui.fragment.base.BaseViewModel
import com.example.photoday.ui.fragment.base.BaseViewModelFactory
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModel
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModelFactory
import com.example.photoday.ui.fragment.gallery.GalleryViewModel
import com.example.photoday.ui.fragment.gallery.GalleryViewModelFactory
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.login.LoginViewModelFactory
import com.example.photoday.ui.fragment.register.RegisterViewModel
import com.example.photoday.ui.fragment.register.RegisterViewModelFactory
import com.example.photoday.ui.fragment.timeline.TimelineViewModel
import com.example.photoday.ui.fragment.timeline.TimelineViewModelFactory

object ViewModelInjector {

    fun providerConfigurationViewModel(
        context: Context?
    ): ConfigurationViewModel {
        return ConfigurationViewModelFactory(context)
            .create(ConfigurationViewModel::class.java)
    }

    fun providerRegisterViewModel(
        context: Context?,
        controlNavigation: NavController
    ): RegisterViewModel {
        return RegisterViewModelFactory(context, controlNavigation)
            .create(RegisterViewModel::class.java)
    }

    fun providerLoginViewModel(
        controlNavigation: NavController,
        context: Context?,
        requireActivity: FragmentActivity,
        lifecycleScope: LifecycleCoroutineScope
    ): LoginViewModel {
        return LoginViewModelFactory(controlNavigation, context, requireActivity, lifecycleScope)
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
        return TimelineViewModelFactory()
            .create(TimelineViewModel::class.java)
    }
}