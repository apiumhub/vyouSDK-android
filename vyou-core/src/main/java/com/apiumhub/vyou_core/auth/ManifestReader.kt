package com.apiumhub.vyou_core.auth

import android.content.Context
import android.content.pm.PackageManager
import java.lang.IllegalArgumentException

internal object ManifestReader {

    private val clientIdKey = "vyou_client_id"
    private val redirectUriKey = "vyou_redirect_uri"
    private val vyouUrl = "vyou_url"

    fun readVyouClientId(context: Context) = readFromManifest(context, clientIdKey)

    fun readVyouRedirectUri(context: Context) = readFromManifest(context, redirectUriKey)

    fun readVyouUrl(context: Context) = readFromManifest(context, vyouUrl)

    private fun readFromManifest(context: Context, key: String) = runCatching {
        context.getString(context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).metaData.getInt(key))
    }.getOrElse {
        throw IllegalArgumentException("$key must be provided in your application's AndroidManifest.xml")
    }

}