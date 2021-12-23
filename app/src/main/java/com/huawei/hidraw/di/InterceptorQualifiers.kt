package com.huawei.hidraw.di

import javax.inject.Qualifier

/**
 * Created by Oguz Sahin on 11/29/2021.
 */

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Header

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class HttpLogging

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NetworkConnection
