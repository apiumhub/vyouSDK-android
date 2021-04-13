package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.VyouCredentials
import com.apiumhub.vyou_core.domain.AuthRepository
import com.apiumhub.vyou_core.facebook.FacebookAuthBody
import com.apiumhub.vyou_core.google.GoogleAuthBody

internal class AuthRetrofitRepository(
    private val vyouApi: VyouApi,
    private val sharedPrefs: CredentialsSharedPrefs,
    private val manifestReader: ManifestReader
) : AuthRepository {

    override val isUserLoggedIn: Boolean
        get() = sharedPrefs.readVyouCredentials() != null

    override suspend fun authenticateWithVyouCode(code: String): VyouCredentials =
        vyouApi
            .webAccessToken(code, manifestReader.readVyouRedirectUri())
            .also {
                sharedPrefs.storeVyouCredentials(it)
            }

    override suspend fun authenticateWithGoogle(googleToken: String): VyouCredentials =
        vyouApi
            .loginWithGoogle(GoogleAuthBody(googleToken, manifestReader.readGoogleClientId()))
            .also {
                sharedPrefs.storeVyouCredentials(it)
            }

    override suspend fun authenticateWithFacebook(facebookToken: String): VyouCredentials =
        vyouApi
            .loginWithFacebook(FacebookAuthBody(facebookToken))
            .also {
                sharedPrefs.storeVyouCredentials(it)
            }

    override fun signOut() = sharedPrefs.clearCredentials()
}