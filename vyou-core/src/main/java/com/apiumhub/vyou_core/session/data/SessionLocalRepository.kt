package com.apiumhub.vyou_core.session.data

import android.webkit.CookieManager
import com.apiumhub.vyou_core.login.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.session.domain.NotAuthenticatedException
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.domain.VyouProfile
import com.apiumhub.vyou_core.session.domain.VyouSession

class SessionLocalRepository(
    private val credentialsSharedPrefs: CredentialsSharedPrefs,
    private val sessionApi: SessionApi,
    private val cookieManager: CookieManager
) : SessionRepository {
    override fun getSession() =
        credentialsSharedPrefs.readVyouCredentials()?.let {
            VyouSession(it)
        }

    override fun storeSession(credentials: VyouCredentials) =
        credentialsSharedPrefs
            .storeVyouCredentials(credentials)
            .run {
                VyouSession(credentials)
            }

    override suspend fun getTenantProfile(): VyouProfile =
        credentialsSharedPrefs.readVyouCredentials()?.let {
            sessionApi.getTenantProfile("Bearer ${it.accessToken}")
        } ?: throw NotAuthenticatedException()

    override suspend fun editProfile(editProfileDto: EditProfileDto) {
        credentialsSharedPrefs.readVyouCredentials()?.let {
            sessionApi.updateProfile("Bearer ${it.accessToken}", editProfileDto)
        } ?: throw NotAuthenticatedException()
    }

    override suspend fun signOut() = credentialsSharedPrefs.readVyouCredentials()?.let {
        sessionApi.signOut("Bearer ${it.accessToken}")
    }.also {
        cookieManager.removeAllCookies(null)
        credentialsSharedPrefs.clearCredentials()
    } ?: Unit
}