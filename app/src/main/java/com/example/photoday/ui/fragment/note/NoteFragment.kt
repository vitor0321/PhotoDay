package com.example.photoday.ui.fragment.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentNoteBinding
import com.example.photoday.ui.adapter.NoteAdapter
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.item.ItemNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoteFragment : BaseFragment() {

    private var _viewDataBinding: FragmentNoteBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: NoteViewModel by viewModel {
        parametersOf(findNavController())
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
        this.viewModel.getAllFirebase().observe(viewLifecycleOwner) { resourceItem ->
            resourceItem.data.apply {
                this?.let { listNote ->
                    initRecycleView(listNote)
                    viewFlipperControl(CHILD_SECOND, PROGRESS_BAR_INVISIBLE)
                }
            }
        }
    }

    private fun initRecycleView(listNote: List<ItemNote>) {
        val spanCount = SPAN_COUNT
        val layoutManagerAdapter = GridLayoutManager(context, spanCount)
        CoroutineScope(Dispatchers.Main).launch {
            viewDataBinding.recycleViewListNote.run {
                layoutManager = layoutManagerAdapter
                adapter = NoteAdapter(context, listNote) { note ->
                    viewModel.navFragment(note)
                }
            }
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

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}
