package com.example.photoday.ui.fragment.base

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.photoday.ui.activity.PhotoDayViewModel
import com.example.photoday.ui.model.item.Components
import org.koin.android.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment : Fragment() {

    private val photoDayViewModel: PhotoDayViewModel by sharedViewModel()
    protected fun statusAppBarNavigationBase(menu: Boolean, components: Components, barColor: Int) {

        /*show OptionsMenu when inflate*/
        setHasOptionsMenu(menu)

        photoDayViewModel.switchComponent = Components(
            components.appBar,
            components.bottomNavigation,
            components.floatingActionButton
        )

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), barColor)
    }

}