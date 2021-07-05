package com.vb.marketing_demo.ui.targeting.model

import android.os.Parcelable
import com.vb.marketing_demo.ui.channels.model.ChannelUI
import kotlinx.parcelize.Parcelize

@Parcelize
data class TargetingUI(
    val title: String,
    val channels: List<ChannelUI>,
    var isSelected: Boolean = false,
) : Parcelable