package com.huawei.hidraw.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Oguz Sahin on 11/15/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    private const val FILE_NAME = "hi_draw_pref_file_name"

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }
}