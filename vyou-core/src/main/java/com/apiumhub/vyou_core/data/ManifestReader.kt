package com.apiumhub.vyou_core.data

import android.content.Context
import android.content.pm.PackageManager
import java.lang.IllegalArgumentException

internal object ManifestReader {

    private val clientIdKey = "vyou_client_id"
    private val redirectUriKey = "vyou_redirect_uri"
    private val vyouUrl = "vyou_url"
    private val googleClientId = "google_client_id"
    private val facebookAppId = "facebook_app_id"

    fun readVyouClientId(context: Context) = readFromManifest(context, clientIdKey)

    fun readVyouRedirectUri(context: Context) = readFromManifest(context, redirectUriKey)

    fun readVyouUrl(context: Context) = readFromManifest(context, vyouUrl)

    fun readGoogleClientId(context: Context) = readFromManifest(context, googleClientId)

    fun readFacebookClientId(context: Context) = readFromManifest(context, facebookAppId)

    private fun readFromManifest(context: Context, key: String) = runCatching {
        context.getString(context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).metaData.getInt(key))
    }.getOrElse {
        throw IllegalArgumentException("$key must be provided in your application's AndroidManifest.xml")
    }



}