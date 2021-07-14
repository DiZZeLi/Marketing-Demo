package com.vb.marketing_demo.ui.campaigns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.marketing_demo.repository.MainRepository
import com.vb.marketing_demo.ui.channels.model.ChannelUI
import com.vb.marketing_demo.utils.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CampaignViewModel(private val repo : MainRepository) : ViewModel() {

    private val _channelsData = MutableLiveData<DataState<List<ChannelUI>>>()
    val channelsData: LiveData<DataState<List<ChannelUI>>>
        get() = _channelsData

    fun getCampaignList(list: List<Int>) {

        repo.getCampaignList(list).onEach {
            _channelsData.value = it
        }.launchIn(viewModelScope)
    }
}