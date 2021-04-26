package com.apiumhub.vyou_core.session.data

import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.session.domain.VYouProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SessionApi {
    @GET("/api/v1/signout")
    suspend fun signOut()

    @GET("/api/v1/customer/tenant/me")
    suspend fun getTenantProfile(): VYouProfile

    @POST("/api/v1/updatecustomer")
    suspend fun updateProfile(@Body editProfileDto: EditProfileDto)

    @POST("/refreshtoken")
    suspend fun refreshToken(@Body token: RefreshTokenDto): VYouCredentials
}