package com.apiumhub.vyou_core.tenant

import com.apiumhub.vyou_core.VYouManager
import com.apiumhub.vyou_core.domain.VYouResult
import com.apiumhub.vyou_core.tenant.domain.RegisterDto
import com.apiumhub.vyou_core.tenant.domain.TenantRepository
import com.apiumhub.vyou_core.tenant.domain.VYouTenant
import org.koin.core.component.inject

class VYouTenantManager internal constructor() : VYouManager() {

    private val tenantRepository: TenantRepository by inject()

    suspend fun tenant(): VYouResult<VYouTenant> = networkCall { tenantRepository.getTenant() }
    suspend fun register(customer: RegisterDto) = networkCall { tenantRepository.createCustomer(customer) }
}