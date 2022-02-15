package com.huawei.hidraw.util.ext

import com.huawei.hidraw.data.model.WinnerModel
import com.huawei.hidraw.ui.adapter.drawwinner.ItemWinnerViewState


fun List<WinnerModel>?.getItemWinnerViewState() = this?.mapIndexed { index, winnerModel ->
    ItemWinnerViewState(
        winnerModel = winnerModel,
        rank = index.plus(1)
    )
}.orEmpty()
