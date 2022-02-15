package com.huawei.hidraw.ui.adapter.drawresult

import com.huawei.hidraw.ui.adapter.drawwinner.ItemWinnerViewState
import com.huawei.hidraw.ui.adapter.title.ItemTitleViewState

sealed class DrawResultAdapterModel {
    data class TitleItem(val itemTitleViewState: ItemTitleViewState) : DrawResultAdapterModel()
    data class WinnerItem(val itemWinnerViewState: ItemWinnerViewState) : DrawResultAdapterModel()
}
