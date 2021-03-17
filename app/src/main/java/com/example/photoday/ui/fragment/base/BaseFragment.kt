package com.example.photoday.ui.fragment.base

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.ui.activity.PhotoDayActivity
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components

abstract class BaseFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerBaseViewModel() }

    protected fun statusAppBarNavigationBase(menu: Boolean, components: Components, barColor: Int) {
        /*show OptionsMenu when inflate*/
        setHasOptionsMenu(menu)

        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val photoActivity = requireActivity() as PhotoDayActivity
        photoActivity.statusAppBarNavigation(components)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), barColor)
    }
}