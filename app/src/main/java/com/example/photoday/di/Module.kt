package com.example.photoday.di

import android.content.Context
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebasePhotos.FirebasePhoto
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.FirebaseAuthRepository
import com.example.photoday.ui.activity.PhotoDayViewModel
import com.example.photoday.ui.databinding.data.UserFirebaseData
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModel
import com.example.photoday.ui.fragment.gallery.GalleryViewModel
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.register.RegisterViewModel
import com.example.photoday.ui.fragment.timeline.TimelineViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModulo = module(override = true) {
    single<BaseRepositoryPhoto> { BaseRepositoryPhoto(get<FirebasePhoto>()) }
    single<BaseRepositoryUser> {
        BaseRepositoryUser(
            get<ChangeUserFirebase>(),
            get<CheckUserFirebase>(),
            get<FirebaseAuthRepository>()
        )
    }
    single<FirebasePhoto> { FirebasePhoto }
    single<ChangeUserFirebase> { ChangeUserFirebase }
    single<CheckUserFirebase> { CheckUserFirebase }
    single<FirebaseAuthRepository> { FirebaseAuthRepository(get<FirebaseAuth>()) }
}

val firebaseRepositoryModulo = module(override = true) {
    single<FirebaseAuth> { Firebase.auth }
}

val uiModulo = module(override = true) {
    single<ForgotPasswordDialog> { ForgotPasswordDialog(get<BaseRepositoryUser>()) }
    single<NewUserNameDialog> {
        NewUserNameDialog(
            get<BaseRepositoryUser>(),
            get<UserFirebaseData>()
        )
    }
}

val viewModelModulo = module(override = true) {

    viewModel<PhotoDayViewModel> {
        PhotoDayViewModel(
            repository = get<BaseRepositoryPhoto>()
        )
    }
    viewModel<ConfigurationViewModel> { (navFragment: NavController) ->
        ConfigurationViewModel(
            repository = get<BaseRepositoryUser>(),
            navFragment = navFragment,
            context = get<Context>()
        )
    }
    viewModel<GalleryViewModel> { (navFragment: NavController) ->
        GalleryViewModel(
            repository = get<BaseRepositoryPhoto>(),
            navFragment = navFragment
        )
    }
    viewModel<LoginViewModel> { (navFragment: NavController) ->
        LoginViewModel(
            repository = get<BaseRepositoryUser>(),
            navFragment = navFragment
        )
    }
    viewModel<RegisterViewModel> { (navFragment: NavController) ->
        RegisterViewModel(
            repository = get<BaseRepositoryUser>(),
            navFragment = navFragment
        )
    }
    viewModel<TimelineViewModel> { (navFragment: NavController) ->
        TimelineViewModel(
            repository = get<BaseRepositoryPhoto>(),
            navFragment = navFragment
        )
    }
}
