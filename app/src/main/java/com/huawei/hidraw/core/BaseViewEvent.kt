package com.huawei.hidraw.core

import androidx.annotation.StringRes
import androidx.navigation.NavDirections

/**
 * Created by Oguz Sahin on 10/26/2021.
 */
sealed class BaseViewEvent {

    data class ShowError(val message: String) : BaseViewEvent()

    data class ShowSuccess(val message: String) : BaseViewEvent()

    data class ShowSuccessWithId(@StringRes val message: Int) : BaseViewEvent()

    object ShowGeneralError : BaseViewEvent()

    object ShowConnectionError : BaseViewEvent()

    data class NavigateTo(val directions: NavDirections) : BaseViewEvent()

}