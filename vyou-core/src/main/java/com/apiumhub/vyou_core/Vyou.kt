package com.apiumhub.vyou_core

import android.app.Application
import androidx.activity.result.ActivityResultCaller
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.di.retrofitModule
import com.apiumhub.vyou_core.di.sharedPrefsModule
import com.apiumhub.vyou_core.di.vyouCoreModule
import com.apiumhub.vyou_core.login.VyouLoginManager
import com.apiumhub.vyou_core.session.di.sessionModule
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.domain.VyouSession
import com.apiumhub.vyou_core.tenant.VyouTenantManager
import com.facebook.FacebookSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

object Vyou : KoinComponent {

    private val sessionRepository: SessionRepository by inject()
    private val koinModules = listOf(retrofitModule, vyouCoreModule, sharedPrefsModule, sessionModule)

    fun initialize(application: Application) {
        startKoin {
            androidContext(application)
            loadKoinModules(koinModules)
        }
        val manifestReader = ManifestReader(application)
        FacebookSdk.setApplicationId(manifestReader.readFacebookClientId())
        FacebookSdk.sdkInitialize(application)
    }

    fun getLogin(actResultCaller: ActivityResultCaller) = VyouLoginManager(actResultCaller)
    val tenantManager by lazy { VyouTenantManager() }
    val session: VyouSession?
        get() = sessionRepository.getSession()
}