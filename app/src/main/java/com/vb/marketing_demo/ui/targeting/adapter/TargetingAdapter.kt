package com.vb.marketing_demo.ui.targeting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vb.marketing_demo.databinding.RecyclerItemTargetingBinding
import com.vb.marketing_demo.ui.targeting.model.TargetingUI

class TargetingAdapter(private val onItemSelected: () -> Unit) :
    ListAdapter<TargetingUI, TargetingAdapter.ViewHolder>(TargetingDiffCallback) {

    inner class ViewHolder(private val binding: RecyclerItemTargetingBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: TargetingUI) {
            binding.targetingCheckbox.text = item.title
            binding.targetingCheckbox.isChecked = item.isSelected
            binding.targetingCheckbox.setOnCheckedChangeListener { _, isChecked ->
                getItem(adapterPosition).isSelected = isChecked
                onItemSelected.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerItemTargetingBinding.inflate(
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

object TargetingDiffCallback : DiffUtil.ItemCallback<TargetingUI>() {
    override fun areItemsTheSame(oldItem: TargetingUI, newItem: TargetingUI): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TargetingUI, newItem: TargetingUI): Boolean {
        return oldItem.title == newItem.title
    }
}