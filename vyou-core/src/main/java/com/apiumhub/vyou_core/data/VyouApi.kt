package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.VyouCredentials
import retrofit2.http.GET
import retrofit2.http.Query

interface VyouApi {
    @GET("/webaccesstoken")
    suspend fun webAccessToken(@Query("code") code: String, @Query("redirect_uri") redirectUri: String): VyouCredentials
}