package com.huawei.hidraw.network.interceptor

import android.content.Context
import com.huawei.hidraw.data.model.NoConnectionException
import com.huawei.hidraw.network.NetworkUtils.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Oguz Sahin on 11/12/2021.
 */

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isNetworkAvailable(context).not()) {
            throw NoConnectionException()
        }
        val newRequestBuilder = chain.request().newBuilder()
        return chain.proceed(newRequestBuilder.build())
    }
}
