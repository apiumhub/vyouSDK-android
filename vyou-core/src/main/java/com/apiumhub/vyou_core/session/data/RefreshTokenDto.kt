package com.apiumhub.vyou_core.session.data

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("token")
    private val token: String,
    @SerializedName("google_id")
    private val googleId: String
)