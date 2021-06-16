package com.apiumhub.vyou_core.session

import com.apiumhub.vyou_core.VYouManager
import com.apiumhub.vyou_core.domain.VYouResult.Failure
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.login.vyou_auth.VYouAuthException
import com.apiumhub.vyou_core.session.data.EditProfileDto
import com.apiumhub.vyou_core.session.domain.SessionRepository
import org.koin.core.component.inject

class VYouSession internal constructor(val credentials: VYouCredentials) : VYouManager() {

    private val sessionRepository: SessionRepository by inject()

    suspend fun signOut() = networkCall { sessionRepository.signOut() }

    suspend fun tenantProfile() = networkCall { sessionRepository.getTenantProfile() }

    suspend fun editProfile(editProfileDto: EditProfileDto) = networkCall { sessionRepository.editProfile(editProfileDto) }
}