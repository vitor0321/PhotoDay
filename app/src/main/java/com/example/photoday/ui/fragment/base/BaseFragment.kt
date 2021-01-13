package com.example.photoday.ui.fragment.base

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.navigation.Navigation.navFragmentConfigurationToTimeline
import com.example.photoday.ui.navigation.Navigation.navFragmentGalleryToConfiguration
import com.example.photoday.ui.navigation.Navigation.navFragmentTimelineToConfiguration

abstract class BaseFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerBaseViewModel() }
    private val navFragment by lazy { findNavController() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarNavigation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
        return super.onOptionsItemSelected(item)
    }

    private fun statusBarNavigation() {
        /*para aparecer o menu quando for inflado*/
        setHasOptionsMenu(true)
        arguments?.let {}

        /*mudar a cor do statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange)
    }
}