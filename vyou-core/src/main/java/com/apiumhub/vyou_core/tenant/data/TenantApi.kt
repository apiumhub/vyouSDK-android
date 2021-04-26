package com.apiumhub.vyou_core.tenant.data

import com.apiumhub.vyou_core.tenant.domain.CreateCustomerDto
import com.apiumhub.vyou_core.tenant.domain.VYouTenant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TenantApi {
    @GET("/api/v1/tenant")
    suspend fun getTenant(): VYouTenant

    @POST("/api/v1/customer")
    suspend fun createCustomer(@Body customer: CreateCustomerDto)

}