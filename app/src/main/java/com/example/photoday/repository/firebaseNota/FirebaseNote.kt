package com.example.photoday.repository.firebaseNota

import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.ui.model.item.ItemNote
import com.example.photoday.ui.model.resource.ResourceItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FirebaseNote(
    private val firebaseFirestore: FirebaseFirestore,
    auth: FirebaseAuth
) {
    private val authUser = auth.currentUser

    fun saveFirestore(note: ItemNote) = MutableLiveData<ResourceItem<Int>>().apply {
        NoteDocument(
            date = note.date,
            title = note.title,
            note = note.note
        ).also { itemNote ->
            try {
                firebaseFirestore.collection(authUser.email)
                    .document().apply {
                        this.set(itemNote)
                        ResourceItem(R.string.nota_add)
                    }
            } catch (e: Exception) {
                ResourceItem(R.string.error_api)
            }
        }
    }

    fun getForId(id: String) = MutableLiveData<ResourceItem<ItemNote>>().apply {
        firebaseFirestore.collection(authUser.email)
            .document(id)
            .addSnapshotListener { s, _ ->
                s?.let { document ->
                    document.toObject<NoteDocument>()?.toNota(document.id)
                        ?.let { note ->
                            value = ResourceItem(note)
                        }
                }
            }
    }

    fun getAllFirestore() = MutableLiveData<ResourceItem<List<ItemNote>>>().apply {
        val noteList: ArrayList<ItemNote> = ArrayList()
        firebaseFirestore.collection(authUser.email)
            .addSnapshotListener { s, _ ->
                s?.let { snapshot ->
                    val note: List<ItemNote> = snapshot.documents.mapNotNull { document ->
                        document.toObject<NoteDocument>()?.toNota(document.id)
                    }
                    note.map { noteList.add(it) }
                    noteList.sortBy { it.date }
                    val listReverse = noteList.asReversed()
                    value = ResourceItem(listReverse)
                }
            }
    }

    fun delete(noteId: String) = MutableLiveData<ResourceItem<Int>>().apply {
        firebaseFirestore.collection(authUser.email)
            .document(noteId)
            .delete()
            .isSuccessful.apply {
                value = ResourceItem(R.string.nota_delete)
            }
    }

    private class NoteDocument(
        val date: String = "",
        val title: String = "",
        val note: String = ""
    ) {
        fun toNota(id: String): ItemNote = ItemNote(
            id = id,
            date = date,
            title = title,
            note = note
        )
    }
}
