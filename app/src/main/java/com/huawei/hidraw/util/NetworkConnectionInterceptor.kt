package com.huawei.hidraw.util

import android.content.Context
import com.huawei.hidraw.data.model.NoConnectionException
import com.huawei.hidraw.util.NetworkUtils.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Oguz Sahin on 11/12/2021.
 */

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (isNetworkAvailable(context).not()) {
            throw NoConnectionException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}