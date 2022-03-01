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
