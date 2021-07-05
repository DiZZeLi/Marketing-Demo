package com.vb.marketing_demo.ui.campaigns

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vb.marketing_demo.R
import com.vb.marketing_demo.const.CHANNELS_ID_BUNDLE_TAG
import com.vb.marketing_demo.const.PLANS_BUNDLE_TAG
import com.vb.marketing_demo.databinding.FragmentCampaignsBinding
import com.vb.marketing_demo.ui.campaigns.adapter.CampaignsAdapter
import com.vb.marketing_demo.ui.campaigns.model.PlanUI
import com.vb.marketing_demo.ui.channels.model.ChannelUI
import com.vb.marketing_demo.utils.state.DataState

class CampaignsFragment : Fragment(R.layout.fragment_campaigns) {

    private val campaignViewModel: CampaignViewModel by viewModels()
    private var _binding: FragmentCampaignsBinding? = null
    private val binding get() = _binding!!

    private var adapter: CampaignsAdapter? = null

    private var dataSource: List<ChannelUI> = listOf()
    private var argumentsData: List<Int> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        argumentsData = arguments?.getIntegerArrayList(CHANNELS_ID_BUNDLE_TAG) ?: emptyList()
        argumentsData.let { campaignViewModel.getCampaignList(it) }

        adapter = CampaignsAdapter(::listenForButtonEnabling)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCampaignsBinding.bind(view)

        binding.campaignsRecycler.layoutManager = LinearLayoutManager(context)
        binding.campaignsRecycler.adapter = adapter

        campaignViewModel.channelsData.observe(viewLifecycleOwner, { dataState ->

            when (dataState) {
                is DataState.Loading -> {
                    binding.campaignsProgress.isVisible = true
                }
                is DataState.Error -> {
                    binding.campaignsProgress.isVisible = false
                    binding.messageTextview.text = dataState.exception.localizedMessage
                }
                is DataState.Success -> {
                    binding.actionsLinearCardView.isVisible = true
                    binding.campaignsProgress.isVisible = false
                    dataSource = dataState.data
                    adapter?.submitList(dataState.data)

                }
            }

        })

        binding.nextButton.setOnClickListener {
            var counter = 0
            val arrayToSend = arrayListOf<PlanUI>()

            dataSource.forEach { channelUI ->

                channelUI.plans?.forEach {
                    if (it.isSelected) {
                        arrayToSend.add(it)
                        counter++
                    }
                }

            }

            if (counter == dataSource.size) {
                findNavController().navigate(
                    R.id.action_campaignsFragment_to_summaryFragment,
                    bundleOf(Pair(PLANS_BUNDLE_TAG, arrayToSend))
                )
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.select_all_plans),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.clearButton.setOnClickListener {
            dataSource.forEach { channelUI ->
                channelUI.plans?.forEach { it.isSelected = false }
            }
            adapter?.notifyDataSetChanged()
        }
        listenForButtonEnabling()
    }


    private fun listenForButtonEnabling() {
        binding.nextButton.isEnabled =
            dataSource.any { channelUi -> channelUi.plans?.any { it.isSelected } == true }
    }
}