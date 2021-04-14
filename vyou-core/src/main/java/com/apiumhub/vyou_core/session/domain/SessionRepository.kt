package com.apiumhub.vyou_core.session.domain

import com.apiumhub.vyou_core.login.domain.VyouCredentials

interface SessionRepository {
    fun getSession(): VyouSession?
    fun storeSession(credentials: VyouCredentials): VyouSession
    fun signOut()
}