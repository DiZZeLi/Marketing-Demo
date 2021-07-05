package com.vb.marketing_demo.ui.channels

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vb.marketing_demo.R
import com.vb.marketing_demo.const.CHANNELS_BUNDLE_TAG
import com.vb.marketing_demo.const.CHANNELS_ID_BUNDLE_TAG
import com.vb.marketing_demo.databinding.FragmentChannelsBinding
import com.vb.marketing_demo.ui.channels.adapter.SelectedTargetingAdapter
import com.vb.marketing_demo.ui.targeting.model.TargetingUI


class ChannelsFragment : Fragment(R.layout.fragment_channels) {

    private var dataSource: List<TargetingUI> = listOf()
    private var _binding: FragmentChannelsBinding? = null
    private val binding get() = _binding!!

    private var adapter: SelectedTargetingAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataSource = arguments?.getParcelableArray(CHANNELS_BUNDLE_TAG)?.map { it as TargetingUI }
            ?: listOf()
        adapter = SelectedTargetingAdapter(::listenForButtonEnabling)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChannelsBinding.bind(view)
        binding.selectedTargetingRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.selectedTargetingRecyclerview.adapter = adapter

        binding.actionsLinearCardView.isVisible = !dataSource.isNullOrEmpty()
        adapter?.submitList(dataSource)


        listenForButtonEnabling()

        binding.nextButton.setOnClickListener {

            val arraySet = mutableSetOf<Int>()
            var counter = 0

            dataSource.forEach {
                it.channels.forEach { channel ->
                    if (channel.isChecked) {
                        arraySet.add(channel.id)
                        counter++
                    }
                }
            }
            val bundle = Bundle()
            bundle.putIntegerArrayList(
                CHANNELS_ID_BUNDLE_TAG,
                ArrayList(arraySet)
            )

            if (counter == dataSource.size) {
                findNavController().navigate(
                    R.id.action_channelsFragment_to_campaignsFragment,
                    bundle
                )

            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.select_all_channels),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun listenForButtonEnabling() {
        binding.nextButton.isEnabled =
            dataSource.any { targetingUI -> targetingUI.channels.any { it.isChecked } }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataSource.forEach {
            it.channels.forEach { channelUI -> channelUI.isChecked = false }
        }
    }
}