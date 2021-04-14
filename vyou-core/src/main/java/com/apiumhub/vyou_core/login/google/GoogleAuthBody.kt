package com.apiumhub.vyou_core.login.google

import com.google.gson.annotations.SerializedName

data class GoogleAuthBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("google_id")
    val googleId: String
)