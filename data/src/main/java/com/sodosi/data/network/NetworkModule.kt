package com.sodosi.data.network

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sodosi.data.BuildConfig
import com.sodosi.data.network.api.SodosiApi
import com.sodosi.domain.usecase.token.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *  NetworkModule.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "http://54.180.106.195:1200/"
    private const val TIME_OUT = 60L

    @Provides
    @Singleton
    fun provideOkHttpClient(
        getTokenUseCase: GetTokenUseCase,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            })
            addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val token = getTokenUseCase()

                    Log.d("TAG", "TOKEN: $token")

                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()

                    return chain.proceed(request)
                }
            })
        }.build()
    }

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideSodosiApi(
        okHttpClient: OkHttpClient
    ): SodosiApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(SodosiApi::class.java)
}
