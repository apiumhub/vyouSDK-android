package com.apiumhub.vyou_core.di

import com.apiumhub.vyou_core.data.*
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.data.RefreshTokenInterceptor
import com.apiumhub.vyou_core.login.data.AuthApi
import com.apiumhub.vyou_core.tenant.data.TenantApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single {
        Retrofit
            .Builder()
            .baseUrl(ManifestReader(androidApplication()).readVYouUrl())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        OkHttpClient
            .Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(ClientCredentialsInterceptor(get()))
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(RefreshTokenInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    single { get<Retrofit>().create(AuthApi::class.java) }
    single { get<Retrofit>().create(TenantApi::class.java) }
    single { Base64Encoder(ManifestReader(androidApplication()).readVYouClientId()) }
}