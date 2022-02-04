package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 12/27/2021.
 */
data class DrawDetailModel(
    val draw: DrawModel,
    val isDrawCreatorMe: Boolean = false
)
