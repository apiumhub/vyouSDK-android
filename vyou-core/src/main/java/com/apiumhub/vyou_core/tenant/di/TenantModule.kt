package com.apiumhub.vyou_core.tenant.di

import com.apiumhub.vyou_core.tenant.data.TenantRetrofitRepository
import com.apiumhub.vyou_core.tenant.domain.TenantRepository
import org.koin.dsl.module

val tenantModule = module {
    single<TenantRepository> {
        TenantRetrofitRepository(get(), get())
    }
}