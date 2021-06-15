package com.example.photoday.repository.firebaseNota

import androidx.lifecycle.MutableLiveData
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.ui.model.item.ItemNote
import com.example.photoday.ui.model.resource.ResourceItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject

class FirebaseNote(
    private val firebaseFirestore: FirebaseFirestore,
    auth: FirebaseAuth
) {
    private val authUser = auth.currentUser

    fun saveFirestore(note: ItemNote) = MutableLiveData<ResourceItem<Void?>>().apply {
        NoteDocument(
            date = note.date,
            title = note.title,
            note = note.note
        ).also { itemNote ->
            try {
                note.id?.let {
                    ResourceItem(data = saveData(note))
                } ?: run {
                    firebaseFirestore.collection(authUser.email)
                        .document()
                        .set(itemNote)
                        .addOnSuccessListener {
                            value = ResourceItem(message = TRUE)
                        }
                        .addOnFailureListener {
                            value = ResourceItem(message = null)
                        }
                }
            } catch (e: Exception) {
                value = ResourceItem(message = null)
            }
        }
    }

    private fun saveData(note: ItemNote) = MutableLiveData<ResourceItem<Any>>().apply {
        try {
            note.id?.let { id ->
                firebaseFirestore.collection(authUser.email)
                    .document(id)
                    .set(note, SetOptions.merge())
                    .addOnSuccessListener {
                        value = ResourceItem(message = TRUE)
                    }
                    .addOnFailureListener {
                        value = ResourceItem(message = FALSE)
                    }
            }
        } catch (e: Exception) {
            value = ResourceItem(message = FALSE)
        }
    }

    fun getForId(id: String) = MutableLiveData<ResourceItem<ItemNote>>().apply {
        firebaseFirestore.collection(authUser.email)
            .document(id)
            .addSnapshotListener { s, _ ->
                s?.let { document ->
                    document.toObject<NoteDocument>()?.toNota(document.id)
                        ?.let { note ->
                            value = ResourceItem(data = note)
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
                    value = ResourceItem(data = listReverse,message = null)
                }
            }
    }

    fun delete(noteId: String) = MutableLiveData<ResourceItem<Any>>().apply {
        firebaseFirestore.collection(authUser.email)
            .document(noteId)
            .delete()
            .addOnSuccessListener {
                value = ResourceItem(message = TRUE)
            }
            .addOnFailureListener {
                value = ResourceItem(message = null)
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
