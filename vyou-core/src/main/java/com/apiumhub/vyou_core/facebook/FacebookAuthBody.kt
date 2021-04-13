package com.apiumhub.vyou_core.facebook

import com.google.gson.annotations.SerializedName

data class FacebookAuthBody(
    @SerializedName("token")
    val token: String
)