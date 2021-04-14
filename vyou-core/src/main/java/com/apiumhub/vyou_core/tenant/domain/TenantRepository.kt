package com.apiumhub.vyou_core.tenant.domain

interface TenantRepository {
    suspend fun getTenant(): VyouTenant
    suspend fun createCustomer(customer: CreateCustomerDto)
}