package com.apiumhub.vyou_core.di

import com.apiumhub.vyou_core.data.AuthRetrofitRepository
import com.apiumhub.vyou_core.domain.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val vyouCoreModule = module {
    single {
        AuthRetrofitRepository(get(), androidContext()) as AuthRepository
    }
}