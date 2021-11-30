package com.huawei.hidraw.core

import android.view.View

open class BaseViewState {

    fun getViewVisibility(isViewVisible: Boolean): Int =
        if (isViewVisible) View.VISIBLE else View.GONE
}