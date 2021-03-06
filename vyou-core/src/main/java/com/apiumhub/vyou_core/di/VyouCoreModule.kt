package com.apiumhub.vyou_core.di

import androidx.activity.result.ActivityResultCaller
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.login.data.LoginRetrofitRepository
import com.apiumhub.vyou_core.login.domain.LoginRepository
import com.apiumhub.vyou_core.login.facebook.FacebookSignInCollaborator
import com.apiumhub.vyou_core.login.google.GoogleSignInCollaborator
import com.apiumhub.vyou_core.login.vyou_auth.VYouSignInCollaborator
import com.apiumhub.vyou_core.tenant.data.TenantRetrofitRepository
import com.apiumhub.vyou_core.tenant.domain.TenantRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val vyouCoreModule = module {
    single {
        ManifestReader(androidApplication())
    }
    single<LoginRepository> {
        LoginRetrofitRepository(authApi = get(), manifestReader = get())
    }
    single<TenantRepository> {
        TenantRetrofitRepository(get(), get())
    }
    single { (p1: ActivityResultCaller) ->
        VYouSignInCollaborator(p1)
    }
    single { (p1: ActivityResultCaller) ->
        GoogleSignInCollaborator(p1, androidContext(), androidApplication())
    }
    single {
        FacebookSignInCollaborator()
    }
}