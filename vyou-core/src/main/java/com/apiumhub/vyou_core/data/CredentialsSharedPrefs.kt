package com.apiumhub.vyou_core.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.apiumhub.vyou_core.VyouCredentials
import com.google.gson.Gson

class CredentialsSharedPrefs(private val sharedPrefs: SharedPreferences, private val gson: Gson) {

    fun storeVyouCredentials(credentials: VyouCredentials) {
        sharedPrefs.edit(true) {
            putString(CREDENTIALS_KEY, gson.toJson(credentials))
        }
    }

    fun readVyouCredentials(): VyouCredentials? =
        runCatching {
            gson.fromJson(sharedPrefs.getString(CREDENTIALS_KEY, ""), VyouCredentials::class.java)
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