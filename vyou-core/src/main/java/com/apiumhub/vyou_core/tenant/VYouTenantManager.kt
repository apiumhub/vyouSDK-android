package com.apiumhub.vyou_core.tenant

import com.apiumhub.vyou_core.domain.VYouResult.Failure
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.tenant.domain.CreateCustomerDto
import com.apiumhub.vyou_core.tenant.domain.TenantRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VYouTenantManager internal constructor() : KoinComponent {

    private val tenantRepository: TenantRepository by inject()

    suspend fun tenant() =
        runCatching { tenantRepository.getTenant() }
            .fold(::Success, ::Failure)

    suspend fun register(customer: CreateCustomerDto) =
        runCatching { tenantRepository.createCustomer(customer) }
            .fold(::Success, ::Failure)
}