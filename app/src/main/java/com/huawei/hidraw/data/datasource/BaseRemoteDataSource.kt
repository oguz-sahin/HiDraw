package com.huawei.hidraw.data.datasource

import com.google.gson.Gson
import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.ResultWrapper.Error
import com.huawei.hidraw.data.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * Created by Oguz Sahin on 11/15/2021.
 */


open class BaseRemoteDataSource constructor(private val ioDispatcher: CoroutineDispatcher) {

    suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResponseModel<T>): ResultWrapper<T> {
        return withContext(ioDispatcher) {
            try {
                val response = apiCall.invoke().data
                ResultWrapper.Success(response)

            } catch (throwable: Throwable) {
                when (throwable) {
                    is NoConnectionException -> Error(throwable)
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        Error(HttpException(errorResponse))
                    }
                    is SocketTimeoutException -> Error(TimeOutException())
                    else -> {
                        Error(GeneralException())
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
            ErrorResponseModel(ErrorModel(throwable.code().toString(), throwable.message()))
        }
    }
}