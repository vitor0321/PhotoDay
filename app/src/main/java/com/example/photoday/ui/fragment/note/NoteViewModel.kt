package com.example.photoday.ui.fragment.note

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.repository.BaseRepositoryNote
import com.example.photoday.ui.model.item.ItemNote

class NoteViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryNote
) : ViewModel() {

    fun getAllFirebase() = repository.getAllFirebase()

    fun salveNota(nota: ItemNote) = repository.saveFirebase(nota)

    fun delete(id: String) = repository.delete(id)

}