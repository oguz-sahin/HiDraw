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

package com.huawei.hidraw.data.datasource.remote

import com.google.gson.Gson
import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.ResultWrapper.Error
import com.huawei.hidraw.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRemoteDataSource {

    internal suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResponseModel<T>): ResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke().data
                ResultWrapper.Success(response)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is NoConnectionException -> Error(value = throwable)
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        Error(value = HttpException(errorResponseModel = errorResponse))
                    }
                    is UnknownHostException -> Error(value = ServiceUnreachableException())
                    is SocketTimeoutException -> Error(value = TimeOutException())
                    else -> {
                        Error(value = GeneralException())
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponseModel? {
        return try {
            throwable.response()?.errorBody()?.string()?.let {
                Gson().fromJson(it, ErrorResponseModel::class.java)
            }
        } catch (exception: Exception) {
            ErrorResponseModel(
                result = ErrorModel(
                    code = throwable.code().toString(),
                    msg = throwable.message()
                )
            )
        }
    }
}
