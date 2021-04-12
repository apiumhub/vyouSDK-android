package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.VyouCredentials
import com.apiumhub.vyou_core.google.GoogleAuthBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VyouApi {
    @GET("/webaccesstoken")
    suspend fun webAccessToken(@Query("code") code: String, @Query("redirect_uri") redirectUri: String): VyouCredentials

    @POST("/googlelogin")
    suspend fun loginWithGoogle(@Body body: GoogleAuthBody): VyouCredentials

}