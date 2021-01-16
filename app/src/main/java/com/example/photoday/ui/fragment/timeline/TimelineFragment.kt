package com.example.photoday.ui.fragment.timeline

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.TimelineListAdapter
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.ui.PhotoDayActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components

class TimelineFragment : BaseFragment() {

    private var _binding: FragmentTimelineBinding? = null
    private val binding: FragmentTimelineBinding get() = _binding!!
    private val viewModel by lazy { ViewModelInjector.providerTimelineViewModel() }
    private lateinit var timelineAdapter: TimelineListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val mainActivity = requireActivity() as PhotoDayActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color of statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(
                requireContext(), R.color.orange_status_bar
        )
    }

    private fun initRecyclerView() {
        binding.apply {
            recycleViewListTimeline.apply {
                layoutManager = LinearLayoutManager(context)
                timelineAdapter = TimelineListAdapter()
                adapter = timelineAdapter
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}