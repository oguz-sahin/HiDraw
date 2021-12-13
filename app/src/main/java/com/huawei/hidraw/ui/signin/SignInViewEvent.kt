package com.huawei.hidraw.ui.signin

/**
 * Created by Oguz Sahin on 11/30/2021.
 */
sealed class SignInViewEvent {
    object SignInWithHuaweiId : SignInViewEvent()
}
