package com.huawei.hidraw.ui.adapter.title

import com.huawei.hidraw.core.BaseViewState

data class ItemTitleViewState(private val title: String) : BaseViewState() {

    fun getTitle() = title

}
