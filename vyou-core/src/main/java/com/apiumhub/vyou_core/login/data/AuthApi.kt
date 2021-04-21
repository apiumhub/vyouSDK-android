package com.apiumhub.vyou_core.login.data

import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.login.facebook.FacebookAuthBody
import com.apiumhub.vyou_core.login.google.GoogleAuthBody
import retrofit2.http.*

interface AuthApi {
    @GET("/webaccesstoken")
    suspend fun webAccessToken(@Query("code") code: String, @Query("redirect_uri") redirectUri: String): VyouCredentials

    @POST("/googlelogin")
    suspend fun loginWithGoogle(@Body body: GoogleAuthBody): VyouCredentials

    @POST("/facebooklogin")
    suspend fun loginWithFacebook(@Body body: FacebookAuthBody): VyouCredentials
}