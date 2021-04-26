package com.apiumhub.vyou_core.session.data

import android.webkit.CookieManager
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.login.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.session.domain.NotAuthenticatedException
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.domain.VYouProfile
import com.apiumhub.vyou_core.session.domain.VYouSession

internal class SessionLocalRepository(
    private val credentialsSharedPrefs: CredentialsSharedPrefs,
    private val sessionApi: SessionApi,
    private val cookieManager: CookieManager,
    private val manifestReader: ManifestReader
) : SessionRepository {
    override fun getSession() =
        credentialsSharedPrefs.readVYouCredentials()?.let {
            VYouSession(it)
        }

    override fun storeSession(credentials: VYouCredentials) =
        credentialsSharedPrefs
            .storeVYouCredentials(credentials)
            .run {
                VYouSession(credentials)
            }

    override suspend fun getTenantProfile(): VYouProfile =
        credentialsSharedPrefs.readVYouCredentials()?.let {
            sessionApi.getTenantProfile()
        } ?: throw NotAuthenticatedException()

    override suspend fun editProfile(editProfileDto: EditProfileDto) {
        credentialsSharedPrefs.readVYouCredentials()?.let {
            sessionApi.updateProfile(editProfileDto)
        } ?: throw NotAuthenticatedException()
    }

    override suspend fun refreshToken() =
        credentialsSharedPrefs.readVYouCredentials()?.let { oldCredentials ->
            sessionApi
                .refreshToken(RefreshTokenDto(oldCredentials.refreshToken, manifestReader.readGoogleClientId()))
                .also(credentialsSharedPrefs::storeVYouCredentials)
        } ?: throw NotAuthenticatedException()

    override suspend fun signOut() = credentialsSharedPrefs.readVYouCredentials()?.let {
        sessionApi.signOut()
    }.also {
        cookieManager.removeAllCookies(null)
        credentialsSharedPrefs.clearCredentials()
    } ?: Unit
}