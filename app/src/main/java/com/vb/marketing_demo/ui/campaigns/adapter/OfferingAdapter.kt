package com.vb.marketing_demo.ui.campaigns.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.vb.marketing_demo.R
import com.vb.marketing_demo.databinding.RecyclerItemOfferingBinding
import com.vb.marketing_demo.ui.campaigns.model.PlanUI


class OfferingAdapter(private val onItemSelected: () -> Unit) :
    ListAdapter<PlanUI, OfferingAdapter.ViewHolder>(OfferingDiffCallback) {


    private var dataSource: List<PlanUI>? = listOf()

    inner class ViewHolder(private val binding: RecyclerItemOfferingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlanUI) {
            if (item.isSelected) {
                setSelected(binding.offeringParentCardView)
            } else {
                setUnselected(binding.offeringParentCardView)
            }

            binding.offeringParentCardView.setOnClickListener {
                if (item.isSelected) {
                    setUnselected(binding.offeringParentCardView)
                } else {
                    setSelected(binding.offeringParentCardView)
                    dataSource?.forEach { it.isSelected = false }
                }
                item.isSelected = !item.isSelected
                notifyDataSetChanged()
                onItemSelected.invoke()
            }

            "${item.plan.price} EUR".also { binding.offeringPrice.text = it }


            var isLeft = true

            var left = ""
            var right = ""

            item.plan.offering.forEach {
                if (isLeft) {
                    left += "$it \n"
                } else {
                    right += "$it \n"
                }
                isLeft = !isLeft
            }

            binding.leftColumnTextview.text = left
            binding.rightColumnTextview.text = right
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerItemOfferingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataSource?.get(position)?.let { holder.bind(it) }
    }

    private fun setUnselected(view: MaterialCardView) {
        view.setCardBackgroundColor(
            ContextCompat.getColor(
                view.context,
                R.color.white
            )
        )
    }

    private fun setSelected(view: MaterialCardView) {
        view.setCardBackgroundColor(
            ContextCompat.getColor(
                view.context,
                R.color.dark_grey
            )
        )
    }

    fun setData(list: List<PlanUI>?) {
        dataSource = list
        submitList(dataSource)
    }
}

object OfferingDiffCallback : DiffUtil.ItemCallback<PlanUI>() {
    override fun areItemsTheSame(oldItem: PlanUI, newItem: PlanUI): Boolean {
        return oldItem.isSelected == newItem.isSelected && oldItem.plan.id == newItem.plan.id
    }

    override fun areContentsTheSame(oldItem: PlanUI, newItem: PlanUI): Boolean {
        return oldItem.plan.price == newItem.plan.price
    }

}

