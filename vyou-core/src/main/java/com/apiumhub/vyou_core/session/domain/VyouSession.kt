package com.apiumhub.vyou_core.session.domain

import com.apiumhub.vyou_core.login.domain.LoginRepository
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.tenant.domain.TenantRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VyouSession internal constructor(private val credentials: VyouCredentials) : KoinComponent {

    private val authRepository: LoginRepository by inject()
    private val tenantRepository: TenantRepository by inject()

    fun signOut() {
//        authRepository.signOut()
    }

    suspend fun getTenant() = tenantRepository.getTenant()
}

/*
    func signout(completion: @escaping ((Result<Void, VYouError>) -> Void)) √
    func register(dto: RegisterDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func editProfile(dto: EditProfileDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func tenantProfile(completion: @escaping ((Result<VYouProfile, VYouError>) -> Void))
    func tenant(completion: @escaping ((Result<VYouTenant, VYouError>) -> Void))
 */