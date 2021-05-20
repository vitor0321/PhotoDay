package com.example.photoday.ui.fragment.fullScreenNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.constants.TRUE_MENU
import com.example.photoday.databinding.FragmentFullScreenNoteBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.item.ItemNote

class FullScreenNoteFragment : BaseFragment() {

    private var _viewDataBinding: FragmentFullScreenNoteBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

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

    private fun init() {
        statusBarNavigation()
        setImageFullScreen()
    }

    private fun setImageFullScreen() {
        this.viewDataBinding.itemNote =
            ItemNote(date = itemDate, title = itemTitle, note = itemNote)
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = TRUE_MENU,
            components = Components(
                appBar = FALSE,
                bottomNavigation = FALSE,
                floatingActionButton = FALSE
            ),
            barColor = R.color.orange_status_bar
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}