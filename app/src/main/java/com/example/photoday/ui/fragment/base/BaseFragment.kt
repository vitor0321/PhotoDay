package com.example.photoday.ui.fragment.base

import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.Utils
import com.example.photoday.ui.PhotoDayActivity
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.navigation.Navigation.navFragmentConfigurationToTimeline
import com.example.photoday.navigation.Navigation.navFragmentGalleryToConfiguration
import com.example.photoday.navigation.Navigation.navFragmentTimelineToConfiguration
import com.example.photoday.ui.stateBarNavigation.Components

abstract class BaseFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerBaseViewModel() }
    private val navFragment by lazy { findNavController() }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.menu_fragment_timeline_app_bar -> {
                    navFragmentTimelineToConfiguration(navFragment)
                }
                R.id.menu_fragment_gallery_app_bar -> {
                    navFragmentGalleryToConfiguration(navFragment)
                }
                R.id.menu_fragment_configuration_app_bar -> {
                    navFragmentConfigurationToTimeline(navFragment)
                }
            }
        }catch (e: Exception) {
            e.message?.let { context?.let { it1 -> Utils.toast(it1, it.toInt()) } }
        }
        return super.onOptionsItemSelected(item)
    }

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