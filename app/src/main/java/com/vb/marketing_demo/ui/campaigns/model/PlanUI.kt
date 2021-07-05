package com.vb.marketing_demo.ui.campaigns.model

import android.os.Parcelable
import com.vb.marketing_demo.api.model.Plan
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanUI(val plan : Plan, var isSelected : Boolean = false, val parentTitle: String) : Parcelable
