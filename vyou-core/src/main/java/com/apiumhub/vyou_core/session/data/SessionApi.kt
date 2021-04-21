package com.apiumhub.vyou_core.session.data

import com.apiumhub.vyou_core.session.domain.VyouProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SessionApi {
    @GET("/api/v1/customer/tenant/me")
    suspend fun getTenantProfile(@Header("Authorization") authorization: String): VyouProfile

    @POST("/api/v1/updatecustomer")
    suspend fun updateProfile(@Header("Authorization") authorization: String, @Body editProfileDto: EditProfileDto)
}