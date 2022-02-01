package com.huawei.hidraw.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Created by Oguz Sahin on 11/12/2021.
 */

object NetworkUtils {

    const val HEADER_KEY = "userId"

    // Service
    const val USER_REQUEST_PATH = "user"
    const val GET_ACTIVE_DRAWS_REQUEST_PATH = "draw/active"
    const val GET_CREATED_DRAW_REQUEST_PATH = "draw/createdDraw"
    const val GET_ATTENDED_DRAW_REQUEST_PATH = "attendDraw/draw"
    const val GET_DRAW_BY_ID_REQUEST_PATH = "draw"
    const val SAVE_PUSH_TOKEN_PATH = "pushToken"

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}
