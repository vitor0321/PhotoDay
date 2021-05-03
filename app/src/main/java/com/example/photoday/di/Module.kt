package com.example.photoday.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.ui.common.ExhibitionCameraOrGallery
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebasePhotos.FirebasePhoto
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.FirebaseAuthRepository
import com.example.photoday.ui.activity.PhotoDayActivity
import com.example.photoday.ui.activity.PhotoDayViewModel
import com.example.photoday.ui.databinding.data.UserFirebaseData
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModel
import com.example.photoday.ui.fragment.gallery.GalleryViewModel
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.register.RegisterViewModel
import com.example.photoday.ui.fragment.timeline.TimelineViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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
    single<FirebasePhoto> { FirebasePhoto(get<FirebaseStorage>()) }
    single<ChangeUserFirebase> { ChangeUserFirebase(get<FirebaseAuth>()) }
    single<CheckUserFirebase> { CheckUserFirebase(get<FirebaseAuth>()) }
    single<FirebaseAuthRepository> {
        FirebaseAuthRepository(
            get<FirebaseAuth>(),
            get<FragmentActivity>(),
            get<Context>()
        )
    }
    single<UserFirebaseData> { UserFirebaseData() }
}

val firebaseRepositoryModulo = module(override = true) {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseStorage> { Firebase.storage }
}

val accessExceptionModulo = module(override = true) {
    single<ExhibitionCameraOrGallery> { ExhibitionCameraOrGallery }
    single<CheckVersionPermission> { CheckVersionPermission }
}

val uiModulo = module(override = true) {
    single<FragmentActivity> { FragmentActivity() }
    single<PhotoDayActivity> { PhotoDayActivity() }
}

val viewModelModulo = module(override = true) {
    viewModel<PhotoDayViewModel> {
        PhotoDayViewModel(
            repository = get<BaseRepositoryPhoto>(),
        )
    }
    viewModel<ConfigurationViewModel> { (navFragment: NavController) ->
        ConfigurationViewModel(
            repository = get<BaseRepositoryUser>(),
            navFragment = navFragment,
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
