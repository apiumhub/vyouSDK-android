package com.apiumhub.vyou_core.auth

import android.content.Context
import android.content.pm.PackageManager
import java.lang.IllegalArgumentException

internal object ManifestReader {

    private val clientIdKey = "vyou_client_id"
    private val redirectUriKey = "vyou_redirect_uri"

    fun readVyouClientId(context: Context): String =
        readFromManifest(context, clientIdKey)

    fun readVyouRedirectUri(context: Context): String =
        readFromManifest(context, redirectUriKey)

    private fun readFromManifest(context: Context, key: String) = runCatching {
        context.getString(context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).metaData.getInt(key))
    }.getOrElse {
        throw IllegalArgumentException("$key must be provided in your application's manifest")
    }
}