package com.apiumhub.vyou_core.session.domain

import com.apiumhub.vyou_core.domain.VyouResult.Failure
import com.apiumhub.vyou_core.domain.VyouResult.Success
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VyouSession internal constructor(val credentials: VyouCredentials) : KoinComponent {

    private val sessionRepository: SessionRepository by inject()

    fun signOut() = runCatching {
        sessionRepository.signOut()
    }.fold(::Success, ::Failure)

    suspend fun tenantProfile() = runCatching {
        sessionRepository.getTenantProfile()
    }.fold(::Success, ::Failure)
}

/*
    func signout(completion: @escaping ((Result<Void, VYouError>) -> Void)) √
    func register(dto: RegisterDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func editProfile(dto: EditProfileDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func tenantProfile(completion: @escaping ((Result<VYouProfile, VYouError>) -> Void))
    func tenant(completion: @escaping ((Result<VYouTenant, VYouError>) -> Void))
 */