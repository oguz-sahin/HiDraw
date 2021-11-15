package com.huawei.hidraw.data.repository

/**
 * Created by Oguz Sahin on 11/15/2021.
 */
interface AuthRepository {
    suspend fun signIn()
    fun isUserLogged(): Boolean
    fun saveToken(token: String)
    fun removeToken()
}