package com.apiumhub.vyou_core.login.data

import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.login.domain.LoginRepository
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.login.facebook.FacebookAuthBody
import com.apiumhub.vyou_core.login.google.GoogleAuthBody

internal class LoginRetrofitRepository(
    private val authApi: AuthApi,
    private val manifestReader: ManifestReader
) : LoginRepository {

    override suspend fun authenticateWithVYouCode(code: String, codeVerifier: String): VYouCredentials =
        authApi.webAccessToken(code, codeVerifier, manifestReader.readVYouRedirectUri())

    override suspend fun authenticateWithGoogle(googleToken: String): VYouCredentials =
        authApi.loginWithGoogle(GoogleAuthBody(googleToken, manifestReader.readGoogleClientId()))

    override suspend fun authenticateWithFacebook(facebookToken: String): VYouCredentials =
        authApi.loginWithFacebook(FacebookAuthBody(facebookToken))
}