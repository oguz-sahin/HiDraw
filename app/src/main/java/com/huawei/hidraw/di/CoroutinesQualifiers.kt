package com.huawei.hidraw.di

import javax.inject.Qualifier

/**
 * Created by Oguz Sahin on 11/15/2021.
 */


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher
