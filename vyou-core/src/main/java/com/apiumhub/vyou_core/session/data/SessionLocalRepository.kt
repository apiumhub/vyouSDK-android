package com.apiumhub.vyou_core.session.data

import com.apiumhub.vyou_core.di.Base64Encoder
import com.apiumhub.vyou_core.login.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.session.domain.NotAuthenticatedException
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.domain.VyouProfile
import com.apiumhub.vyou_core.session.domain.VyouSession

class SessionLocalRepository(
    private val credentialsSharedPrefs: CredentialsSharedPrefs,
    private val base64Encoder: Base64Encoder,
    private val sessionApi: SessionApi
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

    override fun signOut() = credentialsSharedPrefs.clearCredentials()
}