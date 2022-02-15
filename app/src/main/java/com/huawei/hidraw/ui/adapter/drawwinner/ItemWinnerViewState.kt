package com.huawei.hidraw.ui.adapter.drawwinner

import android.content.Context
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.data.model.WinnerModel

data class ItemWinnerViewState(
    private val winnerModel: WinnerModel,
    private val rank: Int
) : BaseViewState() {

    fun getName() = when {
        winnerModel.name.isNullOrBlank().not() -> winnerModel.name
        winnerModel.forumName.isNullOrBlank().not() -> winnerModel.forumName
        else -> ""
    }

    fun getRank(context: Context) = context.getString(R.string.rank, rank)

}
