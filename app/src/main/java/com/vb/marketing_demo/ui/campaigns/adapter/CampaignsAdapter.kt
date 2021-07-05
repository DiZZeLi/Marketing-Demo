package com.vb.marketing_demo.ui.campaigns.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vb.marketing_demo.databinding.RecyclerItemCampaignBinding
import com.vb.marketing_demo.ui.channels.model.ChannelUI

class CampaignsAdapter(private val onItemSelected: () -> Unit) :
    ListAdapter<ChannelUI, CampaignsAdapter.ViewHolder>(CampaignsDiffCallback) {

    inner class ViewHolder(private val binding: RecyclerItemCampaignBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ChannelUI) {
            binding.campaignsTitle.text = item.title

            val adapter = OfferingAdapter(onItemSelected)
            binding.offeringViewPager.adapter = adapter
            adapter.setData(item.plans)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerItemCampaignBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object CampaignsDiffCallback : DiffUtil.ItemCallback<ChannelUI>() {
    override fun areItemsTheSame(oldItem: ChannelUI, newItem: ChannelUI): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChannelUI, newItem: ChannelUI): Boolean {
        return oldItem.title == newItem.title
    }
}