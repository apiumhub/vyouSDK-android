package com.apiumhub.vyou_core.session.di

import android.webkit.CookieManager
import com.apiumhub.vyou_core.session.data.SessionApi
import com.apiumhub.vyou_core.session.data.SessionLocalRepository
import com.apiumhub.vyou_core.session.domain.SessionRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val sessionModule = module {
    single<SessionRepository> { SessionLocalRepository(get(), get(), get(), get()) }
    single<SessionApi> { get<Retrofit>().create(SessionApi::class.java) }
    single { CookieManager.getInstance() }
}