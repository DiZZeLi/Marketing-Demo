package com.vb.marketing_demo.ui.targeting

import androidx.lifecycle.*
import com.vb.marketing_demo.repository.MainRepository
import com.vb.marketing_demo.ui.targeting.model.TargetingUI
import com.vb.marketing_demo.utils.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TargetingViewModel(private val repo: MainRepository) : ViewModel() {

    private val _targetingData = MutableLiveData<DataState<List<TargetingUI>>>()
    val targetingData: LiveData<DataState<List<TargetingUI>>>
        get() = _targetingData


    fun getTargetingList() {
        repo.getTargetingList().onEach {
            _targetingData.value = it
        }.launchIn(viewModelScope)
    }
}
