package com.example.photoday.repository

import com.example.photoday.repository.firebaseNota.FirebaseNote
import com.example.photoday.ui.model.item.ItemNote

class BaseRepositoryNote(
    private val firebaseNota: FirebaseNote
    ) {

    fun saveFirebase(nota: ItemNote) = firebaseNota.saveFirestore(nota)
    fun getForId(id: String) = firebaseNota.getForId(id)
    fun getAllFirebase() = firebaseNota.getAllFirestore()
    fun delete(id: String) = firebaseNota.delete(id)


}
