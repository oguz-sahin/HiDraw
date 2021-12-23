package com.huawei.hidraw.vm

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.CommonBasicResultModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.ui.CreateDrawViewState
import com.huawei.hidraw.ui.DrawTypes
import com.huawei.hidraw.ui.DrawTypes.CUSTOM
import com.huawei.hidraw.ui.DrawTypes.INSTAGRAM
import com.huawei.hidraw.util.InputValidation
import com.huawei.hidraw.util.customdraw.CustomDrawHelper
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

    fun setSelectedEndDate(endDate: String) {
        submitViewState(
            currentViewState.copy(selectedEndDate = endDate)
        )
    }

    fun setDrawType(drawType: DrawTypes) {
        submitViewState(
            currentViewState.copy(selectedDrawType = drawType)
        )
    }

    private fun submitViewState(viewState: CreateDrawViewState) {
        currentViewState = viewState
        _createDrawFragmentViewState.postValue(currentViewState)
    }

    fun createDraw(model: DrawModel): CommonBasicResultModel<String> {

        val type = if (currentViewState.getCustomGroupVisibility() == View.VISIBLE)
            CUSTOM
        else
            INSTAGRAM

        val checkInputsResult = checkDrawInputs(model, type)
        return if (!checkInputsResult.passed) {
            checkInputsResult
        } else {
            if (type == INSTAGRAM) {
                // TODO CALL THE API
                CommonBasicResultModel(true, "Request sended.")
            } else {
                return CustomDrawHelper().draw(model)
            }
        }
    }

    private fun checkDrawInputs(
        model: DrawModel,
        types: DrawTypes
    ): CommonBasicResultModel<String> {
        return InputValidation().checkInputs(model, types)
    }
}
