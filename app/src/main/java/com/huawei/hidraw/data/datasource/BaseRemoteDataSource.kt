package com.huawei.hidraw.data.datasource

import com.google.gson.Gson
import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.BaseResponseModel
import com.huawei.hidraw.data.model.ErrorModel
import com.huawei.hidraw.data.model.ErrorResponseModel
import com.huawei.hidraw.util.NetworkConnectionInterceptor.NoConnectionException
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
                    is NoConnectionException -> ResultWrapper.Error(
                        ErrorResponseModel(
                            ErrorModel(
                                msg = "İnternet Bağlantınızı Kontrol Ediniz"
                            )
                        )
                    )
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.Error(errorResponse)
                    }

                    is SocketTimeoutException -> ResultWrapper.Error(
                        ErrorResponseModel(
                            ErrorModel(
                                msg = "Bağlantı Zaman Aşımına Uğradı"
                            )
                        )
                    )
                    else -> {
                        ResultWrapper.Error(
                            ErrorResponseModel(
                                ErrorModel(
                                    msg = "Bir Sorun Oluştu"
                                )
                            )
                        )
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
            null
        }
    }
}