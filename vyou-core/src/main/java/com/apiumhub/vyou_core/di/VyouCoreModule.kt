package com.apiumhub.vyou_core.di

import androidx.activity.result.ActivityResultCaller
import com.apiumhub.vyou_core.auth.VyouSignInCollaborator
import com.apiumhub.vyou_core.data.AuthRetrofitRepository
import com.apiumhub.vyou_core.domain.AuthRepository
import com.apiumhub.vyou_core.google.GoogleSignInCollaborator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val vyouCoreModule = module {
    single {
        AuthRetrofitRepository(get(), androidContext()) as AuthRepository
    }
    single { (p1: ActivityResultCaller) ->
        VyouSignInCollaborator(p1)
    }
    single { (p1: ActivityResultCaller) ->
        GoogleSignInCollaborator(p1, androidContext())
    }
}