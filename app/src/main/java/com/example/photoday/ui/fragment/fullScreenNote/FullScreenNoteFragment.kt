package com.example.photoday.ui.fragment.fullScreenNote

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.constants.TRUE_MENU
import com.example.photoday.databinding.FragmentFullScreenNoteBinding
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.item.ItemNote
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FullScreenNoteFragment : BaseFragment() {

    private var _viewDataBinding: FragmentFullScreenNoteBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: FullScreenNoteViewModel by viewModel()

    private val itemNoteData: ItemNoteData by inject {
        parametersOf(this)
    }

    private val arguments by navArgs<FullScreenNoteFragmentArgs>()
    private val itemDate by lazy { arguments.itemDate }
    private val itemTitle by lazy { arguments.itemTitle }
    private val itemNote by lazy { arguments.itemNote }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = FragmentFullScreenNoteBinding.inflate(inflater, container, false)
        return this.viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_full_screen, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_full_screen_edit -> viewModel.editNote()
            R.id.menu_full_screen_delete -> viewModel.deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        statusBarNavigation()
        setImageFullScreen()
    }

    private fun setImageFullScreen() {
        val note = ItemNote(date = itemDate, title = itemTitle, note = itemNote)
        this.itemNoteData.setItemNotaData(note)
        this.viewDataBinding.itemNote = itemNoteData
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = TRUE_MENU,
            components = Components(
                appBar = FALSE,
                bottomNavigation = FALSE,
                floatingActionButton = FALSE,
                actionBar = TRUE
            ),
            barColor = R.color.orange_status_bar
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}
