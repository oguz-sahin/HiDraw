package com.huawei.hidraw.di

import android.content.Context
import com.huawei.hidraw.BuildConfig
import com.huawei.hidraw.data.datasource.PrefDataSource
import com.huawei.hidraw.network.DrawService
import com.huawei.hidraw.network.UserService
import com.huawei.hidraw.util.HeaderInterceptor
import com.huawei.hidraw.util.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Oguz Sahin on 11/12/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 120L


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @HttpLogging
    fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @NetworkConnection
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): Interceptor {
        return NetworkConnectionInterceptor(context)
    }

    @Provides
    @Header
    fun provideHeaderInterceptor(prefDataSource: PrefDataSource): Interceptor {
        val userId = prefDataSource.userInfo?.userId ?: ""
        return HeaderInterceptor(userId)
    }

    @Provides
    fun provideOkHttpClient(
        @HttpLogging httpLoggingInterceptor: Interceptor,
        @Header headerInterceptor: Interceptor,
        @NetworkConnection networkConnectionInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(networkConnectionInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideDrawService(retrofit: Retrofit): DrawService {
        return retrofit.create(DrawService::class.java)
    }

}