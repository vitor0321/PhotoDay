package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.constants.DELETE_NOTE
import com.example.photoday.constants.SALVE_NOTE
import com.example.photoday.databinding.DialogFragmentAddNoteBinding
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.model.item.ItemNote
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddNoteDialog(private val itemNote: ItemNote?) : DialogFragment() {

    private var _viewDataBinding: DialogFragmentAddNoteBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val itemNoteData: ItemNoteData by inject {
        parametersOf(this)
    }

    var listenerNote: NoteListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = DialogFragmentAddNoteBinding.inflate(inflater, container, false)
        return this.viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        this.viewDataBinding.apply {
            this.note = itemNoteData
            deleteButton = View.OnClickListener {
                listenerNote?.onNotaSelected(itemNote, DELETE_NOTE)
                onDestroy()
                dialog?.dismiss()
            }
            okButton = View.OnClickListener {
                val titleText = editTextTitle.text.toString()
                val noteText = editTextNote.text.toString()
                when {
                    titleText.isBlank() -> {
                        editTextTitle.error = context?.getString(R.string.enter_title)
                        editTextNote.requestFocus()
                    }
                    noteText.isBlank() -> {
                        editTextNote.error = context?.getString(R.string.enter_note)
                        editTextNote.requestFocus()
                    }
                    else -> {
                        itemNote?.id?.let {id->
                            itemNote.date?.let { date ->
                                val note = ItemNote(
                                    id = id,
                                    date = date,
                                    title = titleText,
                                    note = noteText
                                )
                                listenerNote?.onNotaSelected(note, SALVE_NOTE)
                            }
                        }?:run {
                            itemNote?.date?.let { date ->
                                val note = ItemNote(
                                    date = date,
                                    title = titleText,
                                    note = noteText
                                )
                                listenerNote?.onNotaSelected(note, SALVE_NOTE)
                            }
                        }
                        onDestroy()
                        dialog?.dismiss()
                    }
                }
            }
            cancelButton = View.OnClickListener {
                onDestroy()
                dialog?.dismiss()
            }
        }
    }

    interface NoteListener {
        fun onNotaSelected(nota: ItemNote?, typeDialog: String?)
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(itemNote: ItemNote?) = AddNoteDialog(itemNote)
    }
}