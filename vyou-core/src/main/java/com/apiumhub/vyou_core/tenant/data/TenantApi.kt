package com.apiumhub.vyou_core.tenant.data

import com.apiumhub.vyou_core.tenant.domain.CreateCustomerDto
import com.apiumhub.vyou_core.tenant.domain.VyouTenant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TenantApi {
    @GET("/api/v1/tenant")
    suspend fun getTenant(@Header("Authorization") basicAuth: String): VyouTenant

    @POST("/api/v1/customer")
    suspend fun createCustomer(@Header("Authorization") basicAuth: String, @Body customer: CreateCustomerDto)

}