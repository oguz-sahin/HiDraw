package com.huawei.hidraw.data

import com.huawei.hidraw.data.model.BaseException

/**
 * Created by Oguz Sahin on 11/15/2021.
 */

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val value: BaseException) : ResultWrapper<Nothing>()
}
