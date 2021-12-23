package com.huawei.hidraw.ui

import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.ui.DrawTypes.CUSTOM
import com.huawei.hidraw.ui.DrawTypes.INSTAGRAM

data class CreateDrawViewState(
    private val selectedDrawType: DrawTypes,
    val selectedStartDate: String? = null,
    val selectedEndDate: String? = null
) : BaseViewState() {

    fun getInstagramGroupVisibility() = getViewVisibility(selectedDrawType == INSTAGRAM)

    fun getCustomGroupVisibility() = getViewVisibility(selectedDrawType == CUSTOM)
}

enum class DrawTypes {
    INSTAGRAM, CUSTOM
}
