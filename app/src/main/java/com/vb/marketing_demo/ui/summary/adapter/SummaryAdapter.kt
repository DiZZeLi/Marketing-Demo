package com.vb.marketing_demo.ui.summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vb.marketing_demo.databinding.RecyclerItemSummaryBinding
import com.vb.marketing_demo.ui.campaigns.model.PlanUI

class SummaryAdapter :
    ListAdapter<PlanUI, SummaryAdapter.ViewHolder>(SummaryDiffCallback) {

    inner class ViewHolder(private val binding: RecyclerItemSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: PlanUI) {
            binding.planTitle.text = item.parentTitle
            "Price: ${item.plan.price} EUR".also { binding.planPrice.text = it }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerItemSummaryBinding.inflate(
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

object SummaryDiffCallback : DiffUtil.ItemCallback<PlanUI>() {
    override fun areItemsTheSame(oldItem: PlanUI, newItem: PlanUI): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PlanUI, newItem: PlanUI): Boolean {
        return oldItem.plan.price == newItem.plan.price
    }
}