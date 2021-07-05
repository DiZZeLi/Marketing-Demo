package com.vb.marketing_demo.repository

import com.vb.marketing_demo.api.model.toChannelUi
import com.vb.marketing_demo.api.model.toTargetingUi
import com.vb.marketing_demo.network.RetrofitManager
import com.vb.marketing_demo.ui.channels.model.ChannelUI
import com.vb.marketing_demo.ui.targeting.model.TargetingUI
import com.vb.marketing_demo.utils.state.DataState
import kotlinx.coroutines.flow.*

class MainRepository {

    private val api = RetrofitManager.getApi()

    fun getTargetingList(): Flow<DataState<List<TargetingUI>>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = api.getTargeting()
                val result = response.map { it.toTargetingUi() }
                emit(DataState.Success(result))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }


    fun getCampaignList(list: List<Int>): Flow<DataState<List<ChannelUI>>> {
        return flow {
            emit(DataState.Loading)
            try {
                combine(list.map { flowOf(api.getCampaigns(it)) }) { arrayOfLists -> arrayOfLists.asList() }.collect {
                    emit(DataState.Success(it.map { channel -> channel.toChannelUi() }))
                }
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }
}