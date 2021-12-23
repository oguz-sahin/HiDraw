package com.huawei.hidraw.core

import androidx.annotation.StringRes
import androidx.navigation.NavDirections

/**
 * Created by Oguz Sahin on 10/26/2021.
 */
sealed class BaseViewEvent {

    data class ShowError(val message: String) : BaseViewEvent()

    data class ShowErrorWithId(@StringRes val messageId: Int) : BaseViewEvent()

    data class ShowSuccess(val message: String) : BaseViewEvent()

    data class ShowSuccessWithId(@StringRes val message: Int) : BaseViewEvent()

    data class NavigateTo(val directions: NavDirections) : BaseViewEvent()
}
