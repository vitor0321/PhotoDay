package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_configuration.*

class ConfigurationFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerConfigurationViewModel() }
    private val loginViewModel by lazy {
        val sharedPref by lazy { requireActivity().getPreferences(Context.MODE_PRIVATE) }
        val repositoryShared = LoginRepositoryShared(sharedPref)
        ViewModelInjector.providerLoginViewModel(repositoryShared)
    }
    private val navFragment by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*para aparecer o menu quando for inflado*/
        setHasOptionsMenu(true)
        arguments?.let {}

        /*mudar a cor do statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange)
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_configuration, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        /*Enviando o status do AppBar e do Bottom Navigation para a Activity*/
        val statusAppBarNavigation = Components(TRUE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*set name, emamil e photo do usuário*/
        viewModel.googleSingIn(text_view_user_name, text_view_user_email)

        btn_logout.setOnClickListener {

            viewModel.navFragmentLogin(navFragment)
            loginViewModel.logout()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fragment_configuration_app_bar -> {
                viewModel.navTimeline(navFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
