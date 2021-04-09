package com.apiumhub.vyou_core

import com.google.gson.annotations.SerializedName

data class VyouCredentials(
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("tenant_compliant")
    val tenantCompliant: Boolean,
    @SerializedName("tenant_consent_compliant")
    val tenantConsentCompliant: Boolean,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("scope")
    val scope: String
)