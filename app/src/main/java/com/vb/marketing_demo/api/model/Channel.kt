package com.vb.marketing_demo.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vb.marketing_demo.ui.channels.model.ChannelUI

@kotlinx.parcelize.Parcelize
data class Channel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("plans")
    val plans: List<Plan>?
) : Parcelable

fun Channel.toChannelUi(): ChannelUI {
    return ChannelUI(id, title, plans?.map { it.toPlanUi(title) })
}