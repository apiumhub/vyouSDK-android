package com.apiumhub.vyou

import android.app.Application
import com.apiumhub.vyou_core.login.data.CredentialsStorage
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_ui.VYouUI
import org.koin.dsl.module

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VYouUI.initialize(this, listOf(/*credentialsModule*/))
    }
}

val credentialsModule = module {
    single<CredentialsStorage>(override = true) {
        InMemoryCredentialsStorage()
    }
}

class InMemoryCredentialsStorage: CredentialsStorage {

    private var credentials: VYouCredentials? = null

    override fun save(credentials: VYouCredentials) {
        this.credentials = credentials
    }

    override fun read(): VYouCredentials? = credentials

    override fun clear() {
        credentials = null
    }
}