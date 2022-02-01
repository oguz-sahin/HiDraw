package com.huawei.hidraw.di

import android.content.Context
import com.huawei.hms.aaid.HmsInstanceId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object PushKitModule {

    @Provides
    fun provideHmsInstanceId(
        @ApplicationContext context: Context
    ): HmsInstanceId {
        return HmsInstanceId.getInstance(context)
    }

}

