package com.huawei.hidraw.data.model

import androidx.annotation.StringRes
import com.huawei.hidraw.R

/**
 * Created by Oguz Sahin on 11/29/2021.
 */

open class BaseException : Throwable() {
    open val messageId: Int = R.string.sign_in
}

data class HttpException(val errorResponseModel: ErrorResponseModel?) : BaseException()


class GeneralException : BaseException() {
    override val messageId: Int
        get() = R.string.general_exception_message
}


class NoConnectionException : BaseException() {
    override val messageId: Int
        get() = R.string.no_connection_exception_message
}


class ServiceUnreachableException : BaseException() {
    override val messageId: Int
        get() = R.string.service_unreachable_exception_message
}

class TimeOutException : BaseException() {
    override val messageId: Int
        get() = R.string.time_out_exception_message
}

data class CustomException(@StringRes val exceptionRes: Int) : BaseException() {
    override val messageId: Int
        get() = exceptionRes
}



