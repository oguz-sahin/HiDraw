package com.huawei.hidraw.network.interceptor

import com.huawei.hidraw.network.NetworkUtils.HEADER_KEY
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Oguz Sahin on 11/29/2021.
 */
class HeaderInterceptor(private val userId: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .header(HEADER_KEY, userId)
            .build()
        return chain.proceed(newRequest)
    }
}