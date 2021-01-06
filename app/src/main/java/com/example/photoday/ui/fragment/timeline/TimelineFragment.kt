package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.TimelineListAdapter
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateBarNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_timeline.*

class TimelineFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerTimelineViewModel() }
    private lateinit var timelineAdapter: TimelineListAdapter
    private lateinit var binding: FragmentTimelineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimelineBinding.bind(view)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_timeline, menu)
    }

    private fun init() {
        statusBarNavigation()
        initRecyclerView()
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(TRUE, TRUE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color of statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(
            requireContext(), R.color.orange
        )
    }

    private fun initRecyclerView() {
        binding.run {
            recycleViewListTimeline.apply {
                layoutManager = LinearLayoutManager(context)
                timelineAdapter = TimelineListAdapter()
                adapter = timelineAdapter
            }
        }
    }
}