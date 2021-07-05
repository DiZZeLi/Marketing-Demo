package com.vb.marketing_demo.ui.channels.model

import android.os.Parcelable
import com.vb.marketing_demo.ui.campaigns.model.PlanUI
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChannelUI(
    val id: Int,
    val title: String,
    val plans: List<PlanUI>?,
    var isChecked: Boolean = false
) : Parcelable