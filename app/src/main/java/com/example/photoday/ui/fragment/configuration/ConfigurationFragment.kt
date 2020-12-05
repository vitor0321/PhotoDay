package com.example.photoday.ui.fragment.configuration

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface
import com.example.photoday.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_configuration.*

class ConfigurationFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerConfigurationViewModel() }
    private val loginViewModel by lazy {
        val sharedPref by lazy { requireActivity().getPreferences(Context.MODE_PRIVATE) }
        val repositoryShared = LoginRepositoryShared(sharedPref)
        ViewModelInjector.providerLoginViewModel(repositoryShared)
    }
    private val navFragment by lazy { findNavController() }
    private lateinit var sendDataToActivityInterface: SendDataToActivityInterface

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

    private fun init(){
        /*enviando o status da AppBar e do Navigation a Activity*/
        viewModel.stateAppBarNavigation(sendDataToActivityInterface)

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity: Activity = context as Activity
        /*ativando a interface para enviar dados a fragment*/
        sendDataToActivityInterface = activity as SendDataToActivityInterface
    }
}