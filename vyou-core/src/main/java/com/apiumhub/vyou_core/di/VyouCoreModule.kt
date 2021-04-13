package com.apiumhub.vyou_core.di

import android.app.Application
import android.content.SharedPreferences
import androidx.activity.result.ActivityResultCaller
import com.apiumhub.vyou_core.auth.VyouSignInCollaborator
import com.apiumhub.vyou_core.data.AuthRetrofitRepository
import com.apiumhub.vyou_core.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.domain.AuthRepository
import com.apiumhub.vyou_core.facebook.FacebookSignInCollaborator
import com.apiumhub.vyou_core.google.GoogleSignInCollaborator
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val vyouCoreModule = module {
    single {
        ManifestReader(androidApplication())
    }
    single<AuthRepository> {
        AuthRetrofitRepository(vyouApi = get(), sharedPrefs = get(), manifestReader = get())
    }
    single { (p1: ActivityResultCaller) ->
        VyouSignInCollaborator(p1)
    }
    single { (p1: ActivityResultCaller) ->
        GoogleSignInCollaborator(p1, androidContext(), androidApplication())
    }
    single { (p1: ActivityResultCaller) ->
        FacebookSignInCollaborator()
    }
}