package com.vb.marketing_demo.ui.summary

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vb.marketing_demo.R
import com.vb.marketing_demo.const.PLANS_BUNDLE_TAG
import com.vb.marketing_demo.const.RECIPIENT_MAIL
import com.vb.marketing_demo.databinding.FragmnetSummaryBinding
import com.vb.marketing_demo.ui.campaigns.model.PlanUI
import com.vb.marketing_demo.ui.summary.adapter.SummaryAdapter


class SummaryFragment : Fragment(R.layout.fragmnet_summary) {
    private var _binding: FragmnetSummaryBinding? = null
    private val binding get() = _binding!!


    private var adapter: SummaryAdapter? = null
    private var argumentsData: List<PlanUI> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = SummaryAdapter()
        argumentsData = arguments?.getParcelableArrayList(PLANS_BUNDLE_TAG) ?: emptyList()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmnetSummaryBinding.bind(view)

        binding.summaryRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.summaryRecyclerview.adapter = adapter

        adapter?.submitList(argumentsData)


        var total = 0
        argumentsData.forEach { total += it.plan.price }

        "Total $total EUR".also { binding.totalTextView.text = it }

        binding.newOrderButton.setOnClickListener {
            findNavController().navigate(R.id.action_summaryFragment_to_homeFragment)
        }

        binding.sendMailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {

                var message = "Order details : \n\n "

                argumentsData.forEach {
                    message += "${it.parentTitle}  price: ${it.plan.price} EUR \n"
                }

                message += "\n Total price: $total EUR "

                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(RECIPIENT_MAIL))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
                putExtra(Intent.EXTRA_TEXT, message)

            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.no_app),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

}