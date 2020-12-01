package com.example.photoday.injector

import com.example.photoday.stateAppBarBottonNavigation.StateAppViewModel
import com.example.photoday.stateAppBarBottonNavigation.StateAppViewModelFactory
import com.example.photoday.ui.fragments.register.RegisterViewModel
import com.example.photoday.ui.fragments.register.RegisterViewModelFactory
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.ui.fragments.base.BaseViewModel
import com.example.photoday.ui.fragments.base.BaseViewModelFactory
import com.example.photoday.ui.fragments.calendar.CalendarViewModel
import com.example.photoday.ui.fragments.calendar.CalendarViewModelFactory
import com.example.photoday.ui.fragments.gallery.GalleryViewModel
import com.example.photoday.ui.fragments.gallery.GalleryViewModelFactory
import com.example.photoday.ui.fragments.login.LoginViewModel
import com.example.photoday.ui.fragments.login.LoginViewModelFactory
import com.example.photoday.ui.fragments.timeline.TimelineViewModel
import com.example.photoday.ui.fragments.timeline.TimelineViewModelFatory

object ViewModelInjector {

    fun providerRegisterViewModel(): RegisterViewModel {
        return RegisterViewModelFactory()
            .create(RegisterViewModel::class.java)
    }

    fun providerStateAppViewModel(): StateAppViewModel {
        return StateAppViewModelFactory()
            .create(StateAppViewModel::class.java)
    }

    fun providerLoginViewModel(repositoryShared: LoginRepositoryShared): LoginViewModel{
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