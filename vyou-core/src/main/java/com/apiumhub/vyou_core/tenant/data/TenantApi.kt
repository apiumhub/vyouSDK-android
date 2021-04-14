package com.apiumhub.vyou_core.tenant.data

import com.apiumhub.vyou_core.tenant.domain.VyouTenant
import retrofit2.http.GET
import retrofit2.http.Header

interface TenantApi {
    @GET("/api/v1/tenant")
    suspend fun getTenant(@Header("Authorization") bearerToken: String): VyouTenant
}