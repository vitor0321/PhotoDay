package com.example.photoday.ui.fragment.note

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryNote

class NoteViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryNote
) : ViewModel() {
    fun getAllFirebase() = repository.getAllFirebase()
}