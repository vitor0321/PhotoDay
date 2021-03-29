package com.example.photoday.ui.fragment.fullScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.FALSE_MENU
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.FragmentFullscreenBinding
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import com.example.photoday.ui.adapter.modelAdapter.ItemPhoto
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components

class FullscreenFragment : BaseFragment() {

    private var _viewDataBinding: FragmentFullscreenBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val arguments by navArgs<FullscreenFragmentArgs>()
    private val itemPhoto by lazy { arguments.itemPhoto }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = FragmentFullscreenBinding.inflate(inflater, container, false)
        return this.viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        statusBarNavigation()
        setImageFullScreen()
    }

    private fun setImageFullScreen() {
        this.viewDataBinding.itemFromRecycle = ItemPhoto(photo = itemPhoto)
        this.viewDataBinding.run {
            //Picasso.get().load(itemPhoto).into(imageViewFullScreen)
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
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