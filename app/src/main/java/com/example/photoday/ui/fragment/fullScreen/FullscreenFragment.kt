package com.example.photoday.ui.fragment.fullScreen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.photoday.R
import com.example.photoday.constants.FALSE_MENU
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentFullscreenBinding
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components
import com.squareup.picasso.Picasso
import org.greenrobot.eventbus.EventBus

class FullscreenFragment : BaseFragment(){

    private var _binding: FragmentFullscreenBinding? = null
    private val binding get() = _binding!!

    private val arguments by navArgs<FullscreenFragmentArgs>()
    private val itemPhoto by lazy { arguments.itemPhoto }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFullscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        statusBarNavigation()
        setImageFullScreen()
    }

    private fun setImageFullScreen(){
        binding.run {
            Picasso.get().load(itemPhoto).into(imageViewFullScreen)
        }
    }
    private fun statusBarNavigation() {
        statusAppBarNavigationBase(FALSE_MENU, Components(FALSE_MENU, FALSE_MENU), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}