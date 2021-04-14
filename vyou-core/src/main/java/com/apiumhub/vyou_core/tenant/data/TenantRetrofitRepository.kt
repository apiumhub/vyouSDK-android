package com.apiumhub.vyou_core.tenant.data

import com.apiumhub.vyou_core.di.Base64Encoder
import com.apiumhub.vyou_core.login.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.tenant.domain.CreateCustomerDto
import com.apiumhub.vyou_core.tenant.domain.TenantRepository

class TenantRetrofitRepository(
    private val tenantApi: TenantApi,
    private val base64Encoder: Base64Encoder
) : TenantRepository {

    override suspend fun getTenant() =
        tenantApi.getTenant("Basic ${base64Encoder.vyouClientIdEncodedForAuth}")

    override suspend fun createCustomer(customer: CreateCustomerDto) =
        tenantApi.createCustomer("Basic ${base64Encoder.vyouClientIdEncodedForAuth}", customer)

}