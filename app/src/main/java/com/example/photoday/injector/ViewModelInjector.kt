package com.example.photoday.injector

import com.example.photoday.stateAppBarBottonNavigation.StateAppViewModel
import com.example.photoday.stateAppBarBottonNavigation.StateAppViewModelFactory
import com.example.photoday.ui.fragment.register.RegisterViewModel
import com.example.photoday.ui.fragment.register.RegisterViewModelFactory
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.ui.fragment.base.BaseViewModel
import com.example.photoday.ui.fragment.base.BaseViewModelFactory
import com.example.photoday.ui.fragment.calendar.CalendarViewModel
import com.example.photoday.ui.fragment.calendar.CalendarViewModelFactory
import com.example.photoday.ui.fragment.gallery.GalleryViewModel
import com.example.photoday.ui.fragment.gallery.GalleryViewModelFactory
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.login.LoginViewModelFactory
import com.example.photoday.ui.fragment.timeline.TimelineViewModel
import com.example.photoday.ui.fragment.timeline.TimelineViewModelFatory

object ViewModelInjector {

    fun providerRegisterViewModel(): RegisterViewModel {
        return RegisterViewModelFactory()
            .create(RegisterViewModel::class.java)
    }

    fun providerStateAppViewModel(): StateAppViewModel {
        return StateAppViewModelFactory()
            .create(StateAppViewModel::class.java)
    }

    fun providerLoginViewModel(repositoryShared: LoginRepositoryShared): LoginViewModel {
        return LoginViewModelFactory(repositoryShared)
            .create(LoginViewModel::class.java)
    }

    fun providerBaseViewModel(): BaseViewModel {
        return BaseViewModelFactory()
            .create(BaseViewModel::class.java)
    }

    fun providerCalendarViewModel(): CalendarViewModel {
        return CalendarViewModelFactory()
            .create(CalendarViewModel::class.java)
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