package com.apiumhub.vyou_core.tenant

import com.apiumhub.vyou_core.tenant.domain.TenantRepository
import com.apiumhub.vyou_core.tenant.domain.VyouTenant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VyouTenantManager internal constructor(): KoinComponent {

    private val tenantRepository: TenantRepository by inject()

    suspend fun getTenant(): VyouTenant = tenantRepository.getTenant()

}