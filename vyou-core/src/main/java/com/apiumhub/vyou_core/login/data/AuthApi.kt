package com.apiumhub.vyou_core.login.data

import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_core.login.facebook.FacebookAuthBody
import com.apiumhub.vyou_core.login.google.GoogleAuthBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @GET("/accesstoken")
    suspend fun webAccessToken(
        @Query("code") code: String,
        @Query("code_verifier") codeVerifier: String,
        @Query("redirect_uri") redirectUri: String
    ): VYouCredentials

    @POST("/googlelogin")
    suspend fun loginWithGoogle(@Body body: GoogleAuthBody): VYouCredentials

    @POST("/facebooklogin")
    suspend fun loginWithFacebook(@Body body: FacebookAuthBody): VYouCredentials
}