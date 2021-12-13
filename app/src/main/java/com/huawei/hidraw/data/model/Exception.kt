package com.huawei.hidraw.data.model

import androidx.annotation.StringRes
import com.huawei.hidraw.R
import java.io.IOException

/**
 * Created by Oguz Sahin on 11/29/2021.
 */

interface IException {
    fun getMessage(): Int {
        return 0
    }
}

data class HttpException(val errorResponseModel: ErrorResponseModel?) : IException


class GeneralException : IException {
    override fun getMessage(): Int {
        return R.string.general_exception_message
    }
}


class NoConnectionException : IOException(), IException {
    override fun getMessage(): Int {
        return R.string.no_connection_exception_message
    }
}


class ServiceUnreachableException : IException {
    override fun getMessage(): Int {
        return R.string.service_unreachable_exception_message
    }
}

class TimeOutException : IException {
    override fun getMessage(): Int {
        return R.string.time_out_exception_message
    }
}

data class CustomException(@StringRes val exceptionRes: Int) : IException {
    override fun getMessage(): Int {
        return exceptionRes
    }
}



