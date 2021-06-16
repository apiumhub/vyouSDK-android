package com.apiumhub.vyou_core.session.domain

import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.session.VYouSession
import com.apiumhub.vyou_core.session.data.EditProfileDto

internal interface SessionRepository {
    fun getSession(): VYouSession?
    fun storeSession(credentials: VYouCredentials): VYouSession
    suspend fun getTenantProfile(): VYouProfile
    suspend fun editProfile(editProfileDto: EditProfileDto)
    suspend fun refreshToken(): VYouCredentials
    suspend fun signOut()
}