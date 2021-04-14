package com.apiumhub.vyou_core.session.data

import com.apiumhub.vyou_core.login.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.domain.VyouSession

class SessionLocalRepository(private val credentialsSharedPrefs: CredentialsSharedPrefs) : SessionRepository {
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

    override fun signOut() = credentialsSharedPrefs.clearCredentials()
}