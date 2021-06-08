package com.example.photoday.ui.fragment.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentNoteBinding
import com.example.photoday.ui.adapter.NoteAdapter
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.dialog.AddNoteDialog
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.item.ItemNote
import com.example.photoday.ui.toast.Toast.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoteFragment : BaseFragment(), AddNoteDialog.NoteListener {

    private var _viewDataBinding: FragmentNoteBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: NoteViewModel by viewModel {
        parametersOf(findNavController())
    }

    private val itemNoteData: ItemNoteData by inject {
        parametersOf(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewDataBinding = FragmentNoteBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onResume() {
        super.onResume()
        viewFlipperControl(CHILD_FIRST, PROGRESS_BAR_VISIBLE)
        initObserve()
    }

    private fun init() {
        statusBarNavigation()
    }

    private fun initObserve() {
        viewModel.getAllFirebase().observe(viewLifecycleOwner) { resourceItem ->
            resourceItem.data?.let { listNote ->
                initRecycleView(listNote)
            }
        }
    }

    private fun initRecycleView(listNote: List<ItemNote>?) {
        listNote?.let {
            viewDataBinding.recycleViewListNote.run {
                layoutManager = LinearLayoutManager(context)
                adapter = NoteAdapter(context, listNote) { note ->
                    notaDialog(note)
                }
            }
            viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)
        } ?: run {
            viewFlipperControl(CHILD_FIRST, PROGRESS_BAR_VISIBLE)
        }
    }

    override fun onNotaSelected(note: ItemNote?, typeDialog: String?) {
        when (typeDialog) {
            DELETE_NOTE -> {
                note?.id?.let {
                    viewModel.delete(note.id).observe(viewLifecycleOwner) { resourceItem ->
                        when (resourceItem.message) {
                            TRUE -> messageToast(R.string.note_delete)
                            FALSE -> messageToast(R.string.note_delete_failure)
                        }
                    }
                }
            }
            SALVE_NOTE -> {
                note?.let { itemNota ->
                    this.viewModel.salveNota(itemNota).observe(this) { resourceItem ->
                        when (resourceItem.message) {
                            TRUE -> messageToast(R.string.note_add)
                            FALSE -> messageToast(R.string.note_add_failure)
                        }
                    }
                }
            }
        }
    }

    private fun notaDialog(note: ItemNote) {
        itemNoteData.setItemNotaData(note)
        activity?.let { activity ->
            AddNoteDialog.newInstance(note).apply {
                listenerNote = this@NoteFragment
            }
                .show(activity.supportFragmentManager, ADD_NOTA_DIALOG)
        }
    }

    private fun viewFlipperControl(child: Int, visible: Boolean) {
        when {
            child == CHILD_FIRST && visible == PROGRESS_BAR_VISIBLE -> {
                viewDataBinding.run {
                    viewFlipperNote.displayedChild = CHILD_FIRST
                    progressFlowNote.isVisible = PROGRESS_BAR_VISIBLE
                }
            }
            child == CHILD_SECOND && visible == PROGRESS_BAR_INVISIBLE -> {
                CoroutineScope(Dispatchers.Main).launch {
                    viewDataBinding.run {
                        viewFlipperNote.displayedChild = CHILD_SECOND
                        progressFlowNote.isVisible = PROGRESS_BAR_INVISIBLE
                    }
                }
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = TRUE,
                bottomNavigation = TRUE,
                floatingActionButton = TRUE,
                actionBar = FALSE
            ),
            barColor = R.color.orange_status_bar
        )
    }

    private fun messageToast(message: Int?) {
        message?.let { messageInt ->
            val messageToast = this.getString(messageInt)
            toast(messageToast)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}

