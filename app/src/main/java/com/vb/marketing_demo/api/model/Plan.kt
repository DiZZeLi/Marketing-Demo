package com.vb.marketing_demo.api.model

import android.os.Parcelable
import com.vb.marketing_demo.ui.campaigns.model.PlanUI
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    val id: Int,
    val offering: List<String>,
    val price: Int
) : Parcelable


fun Plan.toPlanUi(title: String): PlanUI {
    return PlanUI(this, parentTitle = title)
}