package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 12/27/2021.
 */
data class DrawDetailModel(
    val draw: DrawModel,
    val winners: List<WinnerModel>?,
    val substitutes: List<WinnerModel>?,
    val createdUser: Boolean,
    val userAttended: Boolean
) {
    companion object {
        fun initial() = DrawDetailModel(
            draw = DrawModel.initial(),
            winners = null,
            substitutes = null,
            createdUser = false,
            userAttended = false
        )
    }
}


