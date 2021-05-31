package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.databinding.DialogFragmentAddNoteBinding
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.databinding.data.UserFirebaseData
import com.example.photoday.ui.model.item.ItemNote
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddNoteDialog(private val itemNote: ItemNote?) : DialogFragment() {

    private var _viewDataBinding: DialogFragmentAddNoteBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    var listener: AddNotaListener? = null

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
            okButton = View.OnClickListener {
                val title = editTextTitle.text.toString()
                val note = editTextNote.text.toString()
                when {
                    title.isBlank() -> {
                        listener?.onNotaSelected(nota = null, R.string.enter_title)
                        editTextNote.requestFocus()
                    }
                    note.isBlank() -> {
                        listener?.onNotaSelected(nota = null, R.string.enter_nota)
                    }
                    else -> {
                        listener?.onNotaSelected(
                            itemNote?.date?.let { date ->
                                ItemNote(
                                    date = date,
                                    title = title,
                                    note = note
                                )
                            },
                            message = null
                        )
                    }
                }
                onDestroy()
                dialog?.dismiss()
            }
            cancelButton = View.OnClickListener {
                onDestroy()
                dialog?.dismiss()
            }
        }
    }

    interface AddNotaListener {
        fun onNotaSelected(nota: ItemNote?, message: Int?)
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(itemNote: ItemNote?) = AddNoteDialog(itemNote)
    }
}