package com.apiumhub.vyou_core.data

import android.app.Application
import android.content.pm.PackageManager

internal class ManifestReader(private val application: Application) {

    private val clientIdKey = "vyou_client_id"
    private val redirectUriKey = "vyou_redirect_uri"
    private val vyouUrl = "vyou_url"
    private val googleClientId = "google_client_id"
    private val facebookAppId = "facebook_app_id"

    fun readVYouClientId() = readFromManifest(clientIdKey)

    fun readVYouRedirectUri() = readFromManifest(redirectUriKey)

    fun readVYouUrl() = readFromManifest(vyouUrl)

    fun readGoogleClientId() = readFromManifest(googleClientId)

    fun readFacebookClientId() = readFromManifest(facebookAppId)

    private fun readFromManifest(key: String) = runCatching {
        application.getString(
            application.packageManager.getApplicationInfo(
                application.packageName, PackageManager.GET_META_DATA).metaData.getInt(key))
    }.getOrElse {
        throw IllegalArgumentException("$key must be provided in your application's AndroidManifest.xml")
    }
}