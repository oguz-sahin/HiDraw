package com.huawei.hidraw.vm

import androidx.lifecycle.MutableLiveData
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.ui.CreateDrawViewState
import com.huawei.hidraw.ui.DrawTypes
import com.huawei.hidraw.ui.DrawTypes.INSTAGRAM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateDrawViewModel @Inject constructor() : BaseViewModel() {

    private val _createDrawFragmentViewState = MutableLiveData(CreateDrawViewState(INSTAGRAM))
    val createDrawFragmentViewState
        get() = _createDrawFragmentViewState


    private var currentViewState = CreateDrawViewState(
        selectedDrawType = INSTAGRAM
    )

    init {
        submitViewState(currentViewState)
    }

    fun setSelectedStartDate(startDate: String) {
        submitViewState(
            currentViewState.copy(selectedStartDate = startDate)
        )
    }

    // TODO startDate and endDate diff check
    fun setSelectedEndDate(endDate: String) {
        submitViewState(
            currentViewState.copy(selectedEndDate = endDate)
        )
    }

    fun setDrawType(drawType: DrawTypes){
        submitViewState(
            currentViewState.copy(selectedDrawType = drawType)
        )
    }

    private fun submitViewState(viewState: CreateDrawViewState) {
        currentViewState = viewState
        _createDrawFragmentViewState.postValue(currentViewState)
    }

}
