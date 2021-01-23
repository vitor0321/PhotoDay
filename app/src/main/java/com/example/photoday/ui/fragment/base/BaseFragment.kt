package com.example.photoday.ui.fragment.base

import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.ui.PhotoDayActivity
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.navigation.Navigation.navFragmentConfigurationToTimeline
import com.example.photoday.ui.navigation.Navigation.navFragmentGalleryToConfiguration
import com.example.photoday.ui.navigation.Navigation.navFragmentTimelineToConfiguration
import com.example.photoday.ui.stateBarNavigation.Components

abstract class BaseFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerBaseViewModel() }
    private val navFragment by lazy { findNavController() }

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

    protected fun statusAppBarNavigationBase(fragment: String) {
        when (fragment) {
            FRAG_CONFIGURATION -> {
                statusType(true, Components(TRUE, FALSE), R.color.orange_status_bar)
            }
            FRAG_GALLERY -> {
                statusType(true, Components(TRUE, TRUE), R.color.orange_status_bar)
            }
            FRAG_LOGIN -> {
                statusType(false, Components(FALSE, FALSE), R.color.white_status_bar)
            }
            FRAG_REGISTER -> {
                statusType(false, Components(FALSE, FALSE), R.color.white_status_bar)
            }
            FRAG_TIMELINE -> {
                statusType(true, Components(TRUE, TRUE), R.color.orange_status_bar)
            }
            FRAG_SPLASH_GOODBYE -> {
                statusType(false, Components(FALSE, FALSE), R.color.white_status_bar)
            }
            FRAG_SPLASH_INTRO -> {
                statusType(false, Components(FALSE, FALSE), R.color.white_status_bar)
            }
            FRAG_SPLASH_LOGIN -> {
                statusType(false, Components(FALSE, FALSE), R.color.white_status_bar)
            }
        }
    }

    private fun statusType(menu: Boolean, components: Components, barColor: Int) {
        /*show OptionsMenu when inflate*/
        setHasOptionsMenu(menu)

        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val mainActivity = requireActivity() as PhotoDayActivity
        mainActivity.statusAppBarNavigation(components)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), barColor)
    }
}