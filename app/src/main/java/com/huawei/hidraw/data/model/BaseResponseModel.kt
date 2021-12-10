package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 11/15/2021.
 */
data class BaseResponseModel<T>(
    val result: Boolean,
    val data: T
)
