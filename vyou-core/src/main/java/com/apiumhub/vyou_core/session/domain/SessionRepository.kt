package com.apiumhub.vyou_core.session.domain

interface SessionRepository {
    fun getSession(): VyouSession?
    fun signOut()
}