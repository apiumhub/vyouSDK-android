package com.apiumhub.vyou_core.di

import com.apiumhub.vyou_core.auth.ManifestReader
import com.apiumhub.vyou_core.data.VyouApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single {
        Retrofit
            .Builder()
            .baseUrl(ManifestReader.readVyouUrl(androidContext()))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
    }

    single {
        get<Retrofit>().create(VyouApi::class.java)
    }
}