package com.michaeludjiawan.testproject.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.michaeludjiawan.testproject.BuildConfig
import com.michaeludjiawan.testproject.data.api.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { createOkHttpClient() }
    single { createGson() }
    single { createWebService<ApiService>(get(), get()) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().build()
}

fun createGson(): Gson {
    return GsonBuilder().create()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, gson: Gson): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    return retrofit.create(T::class.java)
}
