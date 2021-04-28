package com.apiumhub.vyou_core.login.domain

interface LoginRepository {
    suspend fun authenticateWithVYouCode(code: String, codeVerifier: String): VYouCredentials
    suspend fun authenticateWithGoogle(googleToken: String): VYouCredentials
    suspend fun authenticateWithFacebook(facebookToken: String): VYouCredentials
}