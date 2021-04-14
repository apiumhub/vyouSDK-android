package com.apiumhub.vyou_core.session.di

import com.apiumhub.vyou_core.session.data.SessionLocalRepository
import com.apiumhub.vyou_core.session.domain.SessionRepository
import org.koin.dsl.module

val sessionModule = module {
    single<SessionRepository> {
        SessionLocalRepository(get())
    }
}