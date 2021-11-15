package com.huawei.hidraw.network

import retrofit2.http.POST

/**
 * Created by Oguz Sahin on 11/12/2021.
 */

interface UserService {

    @POST("api/user")
    suspend fun saveUser()
}