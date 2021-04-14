package com.apiumhub.vyou_core.tenant.domain

import com.apiumhub.vyou_core.domain.VyouTenant

interface TenantRepository {
    suspend fun getTenant(): VyouTenant
}