package com.apiumhub.vyou_core.di

import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.login.data.AuthApi
import com.apiumhub.vyou_core.tenant.data.TenantApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single {
        Retrofit
            .Builder()
            .baseUrl(ManifestReader(androidApplication()).readVyouUrl())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    single { get<Retrofit>().create(AuthApi::class.java) }
    single { get<Retrofit>().create(TenantApi::class.java) }
    single { Base64Encoder(ManifestReader(androidApplication()).readVyouClientId()) }
}