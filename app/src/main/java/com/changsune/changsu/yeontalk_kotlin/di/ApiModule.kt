package com.changsune.changsu.yeontalk_kotlin.di

import com.changsune.changsu.yeontalk_kotlin.networking.YeonTalkApi
import com.changsune.changsu.yeontalk_kotlin.networking.YeonTalkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "http://52.79.51.149/yeontalk/"

    @Provides
    fun provideYeonTalkApi(): YeonTalkApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(YeonTalkApi::class.java)
    }

    @Provides
    fun provideYeonTalkService(): YeonTalkService {
        return YeonTalkService()
    }
}