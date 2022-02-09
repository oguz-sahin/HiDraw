package com.huawei.hidraw.ui.drawdetail

import com.huawei.hidraw.data.model.WinnerModel

data class ItemWinnerViewState(
    private val userModel: WinnerModel
) {

    fun getName() = when {
        userModel.name.isNullOrBlank().not() -> userModel.name
        userModel.forumName.isNullOrBlank().not() -> userModel.forumName
        else -> ""
    }

}
