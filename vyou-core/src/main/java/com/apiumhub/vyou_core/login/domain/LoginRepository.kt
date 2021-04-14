package com.apiumhub.vyou_core.login.domain

interface LoginRepository {
    suspend fun authenticateWithVyouCode(code: String): VyouCredentials
    suspend fun authenticateWithGoogle(googleToken: String): VyouCredentials
    suspend fun authenticateWithFacebook(facebookToken: String): VyouCredentials
}