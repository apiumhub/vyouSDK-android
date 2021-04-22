package com.apiumhub.vyou_core.session.domain

import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.session.data.EditProfileDto

interface SessionRepository {
    fun getSession(): VyouSession?
    fun storeSession(credentials: VyouCredentials): VyouSession
    suspend fun getTenantProfile(): VyouProfile
    suspend fun editProfile(editProfileDto: EditProfileDto)
    suspend fun signOut()
}