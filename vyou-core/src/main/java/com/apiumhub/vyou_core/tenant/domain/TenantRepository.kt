package com.apiumhub.vyou_core.tenant.domain

interface TenantRepository {
    suspend fun getTenant(): VYouTenant
    suspend fun createCustomer(customer: RegisterDto)
}