package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 11/15/2021.
 */
data class ErrorResponseModel(
    val result: ErrorModel
)

data class ErrorModel(
    val code: String = "",
    val msg: String
)
