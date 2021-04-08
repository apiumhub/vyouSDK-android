package com.apiumhub.vyou_core

data class VyouCredentials(
    val idToken: String,
    val accessToken: String,
    val tokenType: String,
    val refreshToken: String,
    val tenantCompliant: Boolean,
    val tenantConsentCompliant: Boolean,
    val expiresIn: Int,
    val scope: String
)