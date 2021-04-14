package com.apiumhub.vyou_core.tenant.data

import com.apiumhub.vyou_core.login.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.tenant.domain.TenantRepository

class TenantRetrofitRepository(
    private val tenantApi: TenantApi,
    private val credentialsSharedPrefs: CredentialsSharedPrefs
) : TenantRepository {
    override suspend fun getTenant() =
        credentialsSharedPrefs.readVyouCredentials()?.let {
            tenantApi.getTenant("Bearer ${it.accessToken}")
        } ?: throw IllegalStateException("User must be logged in")//TODO
}