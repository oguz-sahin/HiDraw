package com.huawei.hidraw.ui.profile

import com.huawei.hidraw.core.BaseViewState

/**
 * Created by Oguz Sahin on 12/6/2021.
 */
data class DrawListViewState(
    private val isDrawListEmpty: Boolean
) : BaseViewState() {

    fun getErrorVisibility() = getViewVisibility(isViewVisible = isDrawListEmpty)

    fun getListVisibility() = getViewVisibility(isViewVisible = isDrawListEmpty.not())
}
