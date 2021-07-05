package com.vb.marketing_demo.api.model

import com.google.gson.annotations.SerializedName
import com.vb.marketing_demo.ui.targeting.model.TargetingUI

data class Targeting(
    @SerializedName("name")
    val title: String,
    @SerializedName("channels")
    val channels: List<Channel>
)

fun Targeting.toTargetingUi(): TargetingUI {
    return TargetingUI(title, channels.map { it.toChannelUi() })
}