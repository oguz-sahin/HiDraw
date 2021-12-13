package com.huawei.hidraw.ui.profile

import android.view.View

/**
 * Created by Oguz Sahin on 12/6/2021.
 */
data class DrawListViewState(
    private val isDrawListEmpty: Boolean
) {

    fun getErrorVisibility() = if (isDrawListEmpty) View.VISIBLE else View.GONE

    fun getListVisibility() = if (isDrawListEmpty) View.GONE else View.VISIBLE
}
