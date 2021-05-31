package com.example.photoday.ui.fragment.note

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryNote
import com.example.photoday.ui.model.item.ItemNote
import com.example.photoday.ui.navigation.Navigation

class NoteViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryNote
) : ViewModel() {

    fun getAllFirebase() = repository.getAllFirebase()

    fun navFragment(note: ItemNote) {
        Navigation.navFragmentNoteToFullScreenNote(navFragment, note)
    }
}