package com.huawei.hidraw.vm

import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.ui.MainActivityViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    val viewState = MainActivityViewState()
}