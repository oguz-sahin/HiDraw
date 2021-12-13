package com.huawei.hidraw.util.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Created by Oguz Sahin on 12/2/2021.
 */


inline fun <T : ViewBinding> ViewGroup.inflate(
    crossinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T,
    attachToParent: Boolean = false
) =
    bindingInflater.invoke(LayoutInflater.from(this.context), this, attachToParent)