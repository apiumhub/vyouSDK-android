package com.apiumhub.vyou_core.session.domain

import com.apiumhub.vyou_core.domain.VyouResult.Failure
import com.apiumhub.vyou_core.domain.VyouResult.Success
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.session.data.EditProfileDto
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VyouSession internal constructor(val credentials: VyouCredentials) : KoinComponent {

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