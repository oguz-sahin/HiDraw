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

/**
 * Created by Oguz Sahin on 11/15/2021.
 */

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
