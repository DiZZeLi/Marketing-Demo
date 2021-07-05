package com.vb.marketing_demo.api

import com.vb.marketing_demo.api.model.Channel
import com.vb.marketing_demo.api.model.Targeting
import retrofit2.http.GET
import retrofit2.http.Path

interface MarketingApi {

    @GET("targeting.json")
    suspend fun getTargeting(): List<Targeting>

    @GET("channels/{id}.json")
    suspend fun getCampaigns(@Path("id") id: Int): Channel

}