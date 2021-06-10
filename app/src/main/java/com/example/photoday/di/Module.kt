package com.example.photoday.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryNote
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebaseNota.FirebaseNote
import com.example.photoday.repository.firebasePhotos.FirebasePhoto
import com.example.photoday.repository.firebaseUser.ChangeUserFirebase
import com.example.photoday.repository.firebaseUser.CheckUserFirebase
import com.example.photoday.repository.firebaseUser.FirebaseAuthRepository
import com.example.photoday.ui.activity.PhotoDayActivity
import com.example.photoday.ui.activity.PhotoDayViewModel
import com.example.photoday.ui.common.ExhibitionCameraOrGallery
import com.example.photoday.ui.databinding.data.ComponentsData
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.databinding.data.ItemPhotoData
import com.example.photoday.ui.databinding.data.UserFirebaseData
import com.example.photoday.ui.fragment.configuration.ConfigurationViewModel
import com.example.photoday.ui.fragment.fullScreenPhoto.FullScreenPhotoFragment
import com.example.photoday.ui.fragment.fullScreenPhoto.FullScreenPhotoViewModel
import com.example.photoday.ui.fragment.gallery.GalleryAdapter
import com.example.photoday.ui.fragment.gallery.GalleryFragment
import com.example.photoday.ui.fragment.gallery.GalleryViewModel
import com.example.photoday.ui.fragment.login.LoginViewModel
import com.example.photoday.ui.fragment.note.NoteAdapter
import com.example.photoday.ui.fragment.note.NoteFragment
import com.example.photoday.ui.fragment.note.NoteViewModel
import com.example.photoday.ui.fragment.register.RegisterViewModel
import com.example.photoday.ui.fragment.timeline.TimelineAdapter
import com.example.photoday.ui.fragment.timeline.TimelineFragment
import com.example.photoday.ui.fragment.timeline.TimelineViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModulo = module(override = true) {
    single<UserFirebaseData> { UserFirebaseData() }
    single<ComponentsData> { ComponentsData() }
    single<ItemNoteData> { ItemNoteData() }
    single<ItemPhotoData> { ItemPhotoData() }
    single<BaseRepositoryPhoto> {
        BaseRepositoryPhoto(
            get<FirebasePhoto>()
        )
    }
    single<FirebasePhoto> {
        FirebasePhoto(
            get<FirebaseStorage>()
        )
    }
    single<ChangeUserFirebase> {
        ChangeUserFirebase(
            get<FirebaseAuth>()
        )
    }
    single<CheckUserFirebase> {
        CheckUserFirebase(
            get<FirebaseAuth>()
        )
    }
    single<FirebaseNote> {
        FirebaseNote(
            get<FirebaseFirestore>(),
            get<FirebaseAuth>()
        )
    }
    single<BaseRepositoryNote> {
        BaseRepositoryNote(
            get<FirebaseNote>()
        )
    }
    single<BaseRepositoryUser> {
        BaseRepositoryUser(
            get<ChangeUserFirebase>(),
            get<CheckUserFirebase>(),
            get<FirebaseAuthRepository>()
        )
    }
    single<FirebaseAuthRepository> {
        FirebaseAuthRepository(
            get<FirebaseAuth>(),
            get<FragmentActivity>(),
            get<Context>()
        )
    }
}

val firebaseRepositoryModulo = module(override = true) {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseStorage> { Firebase.storage }
    single<FirebaseFirestore> { Firebase.firestore }
}

val accessExceptionModulo = module(override = true) {
    single<ExhibitionCameraOrGallery> { ExhibitionCameraOrGallery }
}

val uiModulo = module(override = true) {
    factory<FragmentActivity> { FragmentActivity() }
    factory<PhotoDayActivity> { PhotoDayActivity() }
    factory<TimelineFragment> { TimelineFragment() }
    factory<GalleryFragment> { GalleryFragment() }
    factory<NoteFragment> { NoteFragment() }
    factory<FullScreenPhotoFragment> { FullScreenPhotoFragment() }
    factory<TimelineAdapter> {TimelineAdapter(get<Context>())  }
    factory<GalleryAdapter> { GalleryAdapter(get<Context>())  }
    factory<NoteAdapter> { NoteAdapter(get<Context>()) }
}

val viewModelModulo = module(override = true) {
    viewModel<PhotoDayViewModel> {
        PhotoDayViewModel(
            photoRepository = get<BaseRepositoryPhoto>(),
            notaRepository = get<BaseRepositoryNote>()
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
    viewModel<NoteViewModel> { (navFragment: NavController) ->
        NoteViewModel(
            navFragment = navFragment,
            repository = get<BaseRepositoryNote>()
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
    viewModel<FullScreenPhotoViewModel> { (date: String, navFragment: NavController) ->
        FullScreenPhotoViewModel(
            navFragment = navFragment,
            repository = get<BaseRepositoryPhoto>(),
            date = date
        )
    }
}
val appModules = listOf(
    repositoryModulo,
    firebaseRepositoryModulo,
    accessExceptionModulo,
    uiModulo,
    viewModelModulo,
)
