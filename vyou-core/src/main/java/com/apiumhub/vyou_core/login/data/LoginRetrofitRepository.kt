package com.apiumhub.vyou_core.login.data

import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.login.domain.LoginRepository
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.login.facebook.FacebookAuthBody
import com.apiumhub.vyou_core.login.google.GoogleAuthBody

internal class LoginRetrofitRepository(
    private val authApi: AuthApi,
    private val manifestReader: ManifestReader
) : LoginRepository {

    override suspend fun authenticateWithVyouCode(code: String): VyouCredentials =
        authApi.webAccessToken(code, manifestReader.readVyouRedirectUri())

    override suspend fun authenticateWithGoogle(googleToken: String): VyouCredentials =
        authApi.loginWithGoogle(GoogleAuthBody(googleToken, manifestReader.readGoogleClientId()))

    override suspend fun authenticateWithFacebook(facebookToken: String): VyouCredentials =
        authApi.loginWithFacebook(FacebookAuthBody(facebookToken))
}