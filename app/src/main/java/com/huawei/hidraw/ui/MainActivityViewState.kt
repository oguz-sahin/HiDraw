package com.huawei.hidraw.ui

import com.huawei.hidraw.core.BaseViewState

data class MainActivityViewState(
    private val isFabVisible: Boolean,
    private val isBottomNavigationVisible: Boolean
) : BaseViewState() {

    fun getFabVisibility() = getViewVisibility(isFabVisible)

    fun getBottomNavigationVisibility() = getViewVisibility(isBottomNavigationVisible)
}
