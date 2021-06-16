package com.apiumhub.vyou_core.session.data

import android.webkit.CookieManager
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.login.data.CredentialsStorage
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.session.domain.NotAuthenticatedException
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.domain.VYouProfile
import com.apiumhub.vyou_core.session.VYouSession

internal class SessionLocalRepository(
    private val credentialsSharedPrefs: CredentialsStorage,
    private val sessionApi: SessionApi,
    private val cookieManager: CookieManager,
    private val manifestReader: ManifestReader
) : SessionRepository {
    override fun getSession() =
        credentialsSharedPrefs.read()?.let {
            VYouSession(it)
        }

    override fun storeSession(credentials: VYouCredentials) =
        credentialsSharedPrefs
            .save(credentials)
            .run { VYouSession(credentials) }

    override suspend fun getTenantProfile(): VYouProfile =
        credentialsSharedPrefs.read()?.let {
            sessionApi.getTenantProfile()
        } ?: throw NotAuthenticatedException()

    override suspend fun editProfile(editProfileDto: EditProfileDto) {
        credentialsSharedPrefs.read()?.let {
            sessionApi.updateProfile(editProfileDto)
        } ?: throw NotAuthenticatedException()
    }

    override suspend fun refreshToken() =
        credentialsSharedPrefs.read()?.let { oldCredentials ->
            sessionApi
                .refreshToken(RefreshTokenDto(oldCredentials.refreshToken, manifestReader.readGoogleClientId()))
                .also(credentialsSharedPrefs::save)
        } ?: throw NotAuthenticatedException()

    override suspend fun signOut() = credentialsSharedPrefs.read()?.let {
        sessionApi.signOut()
    }.also {
        cookieManager.removeAllCookies(null)
        credentialsSharedPrefs.clear()
    } ?: Unit
}