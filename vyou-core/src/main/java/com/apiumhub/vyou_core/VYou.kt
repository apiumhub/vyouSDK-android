package com.apiumhub.vyou_core

import android.app.Application
import androidx.activity.result.ActivityResultCaller
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.di.retrofitModule
import com.apiumhub.vyou_core.di.sharedPrefsModule
import com.apiumhub.vyou_core.di.vyouCoreModule
import com.apiumhub.vyou_core.login.VYouLoginManager
import com.apiumhub.vyou_core.session.di.sessionModule
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.VYouSession
import com.apiumhub.vyou_core.tenant.VYouTenantManager
import com.facebook.FacebookSdk
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object VYou : KoinComponent {

    private val sessionRepository: SessionRepository by inject()
    private val koinModules = listOf(retrofitModule, vyouCoreModule, sharedPrefsModule, sessionModule)

    fun initialize(application: Application, additionalModules: List<Module> = emptyList()) {
        startKoin {
            androidContext(application)
            loadKoinModules(koinModules + additionalModules)
        }
        val manifestReader = ManifestReader(application)
        FacebookSdk.setApplicationId(manifestReader.readFacebookClientId())
        FacebookSdk.sdkInitialize(application)
    }

    fun getLogin(actResultCaller: ActivityResultCaller) = VYouLoginManager(actResultCaller)
    val tenantManager by lazy { VYouTenantManager() }
    val session: VYouSession?
        get() = sessionRepository.getSession()

    val httpClient: OkHttpClient by inject()
}