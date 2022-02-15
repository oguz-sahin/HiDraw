package com.huawei.hidraw.core

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

open class BaseViewState {

    fun getViewVisibility(isViewVisible: Boolean): Int =
        if (isViewVisible) View.VISIBLE else View.GONE

    fun getString(context: Context, @StringRes resId: Int, vararg formatARgs: Any) =
        context.getString(resId, formatARgs)

    fun getString(context: Context, @StringRes resId: Int) = context.getString(resId)

    fun getColor(context: Context, @ColorRes resId: Int) = ContextCompat.getColor(context, resId)
}
