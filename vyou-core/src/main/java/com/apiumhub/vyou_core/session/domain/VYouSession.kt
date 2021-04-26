package com.apiumhub.vyou_core.session.domain

import com.apiumhub.vyou_core.domain.VYouResult.Failure
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.session.data.EditProfileDto
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VYouSession internal constructor(val credentials: VYouCredentials) : KoinComponent {

    private val sessionRepository: SessionRepository by inject()

    suspend fun signOut() = runCatching {
        sessionRepository.signOut()
    }.fold(::Success, ::Failure)

    suspend fun tenantProfile() = runCatching {
        sessionRepository.getTenantProfile()
    }.fold(::Success, ::Failure)

    suspend fun editProfile(editProfileDto: EditProfileDto) = runCatching {
        sessionRepository.editProfile(editProfileDto)
    }.fold(::Success, ::Failure)
}