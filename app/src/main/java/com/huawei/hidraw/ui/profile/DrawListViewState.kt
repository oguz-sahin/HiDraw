package com.huawei.hidraw.ui.profile

import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.data.model.DrawModel

/**
 * Created by Oguz Sahin on 12/6/2021.
 */
data class DrawListViewState(
    private val drawList: List<DrawModel>
) : BaseViewState() {

    private val isDrawListEmpty = drawList.isEmpty()

    fun getErrorVisibility() = getViewVisibility(isViewVisible = isDrawListEmpty)

    fun getListVisibility() = getViewVisibility(isViewVisible = isDrawListEmpty.not())
}
