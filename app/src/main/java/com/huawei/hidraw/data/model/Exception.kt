/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
