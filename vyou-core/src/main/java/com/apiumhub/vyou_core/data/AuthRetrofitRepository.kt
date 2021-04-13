package com.apiumhub.vyou_core.data

import android.content.Context
import com.apiumhub.vyou_core.VyouCredentials
import com.apiumhub.vyou_core.domain.AuthRepository
import com.apiumhub.vyou_core.google.GoogleAuthBody

internal class AuthRetrofitRepository(private val vyouApi: VyouApi, private val context: Context) : AuthRepository {
    override suspend fun authenticateWithVyouCode(code: String): VyouCredentials =
        vyouApi.webAccessToken(code, ManifestReader.readVyouRedirectUri(context))

    override suspend fun authenticateWithGoogle(googleToken: String): VyouCredentials =
        vyouApi.loginWithGoogle(GoogleAuthBody(googleToken, ManifestReader.readGoogleClientId(context)))

    override suspend fun authenticateWithFacebook(facebookToken: String): VyouCredentials = vyouApi.loginWithFacebook()
}