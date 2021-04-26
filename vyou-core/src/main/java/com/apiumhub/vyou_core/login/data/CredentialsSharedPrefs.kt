package com.apiumhub.vyou_core.login.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.google.gson.Gson

class CredentialsSharedPrefs(private val sharedPrefs: SharedPreferences, private val gson: Gson) {

    fun storeVYouCredentials(credentials: VYouCredentials) {
        sharedPrefs.edit(true) {
            putString(CREDENTIALS_KEY, gson.toJson(credentials))
        }
    }

    fun readVYouCredentials(): VYouCredentials? =
        runCatching {
            gson.fromJson(sharedPrefs.getString(CREDENTIALS_KEY, ""), VYouCredentials::class.java)
        }.getOrNull()

    fun clearCredentials() {
        sharedPrefs.edit(true) {
            remove(CREDENTIALS_KEY)
        }
    }

    companion object {
        const val CREDENTIALS_KEY = "vyouCredentialsKey"
    }
}