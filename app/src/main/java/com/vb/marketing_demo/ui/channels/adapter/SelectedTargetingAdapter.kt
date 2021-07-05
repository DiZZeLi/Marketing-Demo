package com.vb.marketing_demo.ui.channels.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vb.marketing_demo.databinding.RecyclerItemChannelBinding
import com.vb.marketing_demo.ui.targeting.model.TargetingUI


class SelectedTargetingAdapter(private val onItemSelected: () -> Unit) :
    ListAdapter<TargetingUI, SelectedTargetingAdapter.ViewHolder>(SelectedTargetingDiffCallback) {

    inner class ViewHolder(private val binding: RecyclerItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TargetingUI) {
            binding.channelTitleTextview.text = item.title

            item.channels.forEach {
                val radioButton = RadioButton(this.itemView.context)
                radioButton.text = it.title


                radioButton.setOnCheckedChangeListener { _, isChecked ->
                    it.isChecked = isChecked
                    onItemSelected.invoke()
                }

                binding.channelsRadioGroup.addView(radioButton)
                radioButton.isChecked = it.isChecked
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerItemChannelBinding.inflate(
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

object SelectedTargetingDiffCallback : DiffUtil.ItemCallback<TargetingUI>() {
    override fun areItemsTheSame(oldItem: TargetingUI, newItem: TargetingUI): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TargetingUI, newItem: TargetingUI): Boolean {
        return oldItem.title == newItem.title
    }
}