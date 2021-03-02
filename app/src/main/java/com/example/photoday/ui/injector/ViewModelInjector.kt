package com.example.photoday.ui.injector

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebasePhotos.FirebasePhoto
import com.example.photoday.ui.activity.PhotoDayViewModel
import com.example.photoday.ui.activity.PhotoDayViewModelFactory
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

    fun providerConfigurationViewModel(repository: BaseRepositoryUser): ConfigurationViewModel {
        return ConfigurationViewModelFactory(repository)
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
            requireActivity: FragmentActivity
    ): LoginViewModel {
        return LoginViewModelFactory(controlNavigation, context, requireActivity)
                .create(LoginViewModel::class.java)
    }

    fun providerBaseViewModel(): BaseViewModel {
        return BaseViewModelFactory()
                .create(BaseViewModel::class.java)
    }

    fun providerGalleryViewModel(repository: BaseRepositoryPhoto): GalleryViewModel {
        return GalleryViewModelFactory(repository)
                .create(GalleryViewModel::class.java)
    }

    fun providerTimelineViewModel(repository: BaseRepositoryPhoto): TimelineViewModel {
        return TimelineViewModelFactory(repository)
                .create(TimelineViewModel::class.java)
    }

    fun providerPhotoDayViewModel(): PhotoDayViewModel {
        return PhotoDayViewModelFactory()
            .create(PhotoDayViewModel::class.java)
    }
}