package com.apiumhub.vyou_core.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.apiumhub.vyou_core.data.CredentialsSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPrefsModule = module {
    single {
        MasterKey
            .Builder(androidContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    single {
        EncryptedSharedPreferences.create(
            androidContext(),
            "VyouCredentialsSharedPrefs",
            get(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single {
        CredentialsSharedPrefs(get())
    }
}