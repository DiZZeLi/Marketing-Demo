package com.vb.marketing_demo.ui.targeting

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vb.marketing_demo.R
import com.vb.marketing_demo.const.CHANNELS_BUNDLE_TAG
import com.vb.marketing_demo.databinding.FragmentHomeBinding
import com.vb.marketing_demo.ui.targeting.adapter.TargetingAdapter
import com.vb.marketing_demo.ui.targeting.model.TargetingUI
import com.vb.marketing_demo.utils.state.DataState

class TargetingFragment : Fragment(R.layout.fragment_home) {


    private val targetingViewModel: TargetingViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var adapter: TargetingAdapter? = null

    private var dataSource: List<TargetingUI> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        targetingViewModel.getTargetingList()
        adapter = TargetingAdapter(::listenForButtonEnabling)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)


        binding.targetingRecycler.layoutManager = LinearLayoutManager(context)
        binding.targetingRecycler.adapter = adapter

        targetingViewModel.targetingData.observe(
            viewLifecycleOwner,
            { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        binding.homeProgress.isVisible = true
                    }
                    is DataState.Success -> {
                        binding.homeProgress.isVisible = false
                        binding.actionsLinearCardView.isVisible = true
                        dataSource = dataState.data
                        adapter?.submitList(dataSource)
                    }
                    is DataState.Error -> {
                        binding.homeProgress.isVisible = false
                        "An error occurred".also { binding.messageTextview.text = it }
                    }
                }

            },
        )

        binding.nextButton.setOnClickListener {

            val bundle = Bundle()
            val listWithSelections = dataSource.filter { it.isSelected } as? ArrayList<TargetingUI>

            bundle.putParcelableArray(CHANNELS_BUNDLE_TAG, listWithSelections?.toTypedArray())

            findNavController().navigate(R.id.action_homeFragment_to_channelsFragment, bundle)
        }

        listenForButtonEnabling()
    }


    private fun listenForButtonEnabling() {
        binding.nextButton.isEnabled = dataSource.any { targetingUI -> targetingUI.isSelected }
    }
}