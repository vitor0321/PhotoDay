package com.example.photoday.ui.fragment.fullScreenPhoto

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.FALSE_MENU
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentFullScreenPhotoBinding
import com.example.photoday.ui.databinding.data.ItemPhotoData
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.item.ItemPhoto
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FullScreenPhotoFragment : BaseFragment() {

    private var _viewDataBinding: FragmentFullScreenPhotoBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: FullScreenPhotoViewModel by viewModel()

    private val arguments by navArgs<FullScreenPhotoFragmentArgs>()
    private val itemPhoto by lazy { arguments.itemPhoto }
    private val itemDate by lazy { arguments.itemDate }
    private val itemPhotoData: ItemPhotoData by inject{
        parametersOf(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = FragmentFullScreenPhotoBinding.inflate(inflater, container, false)
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
        val note = ItemPhoto(date = itemDate, photo = itemPhoto)
        this.itemPhotoData.setItemPhotoData(note)
        this.viewDataBinding.itemPhoto = itemPhotoData
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
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