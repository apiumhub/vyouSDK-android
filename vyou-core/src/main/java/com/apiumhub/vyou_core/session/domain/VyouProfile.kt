package com.apiumhub.vyou_core.session.domain

import com.google.gson.annotations.SerializedName

data class VyouProfile (
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("mandatory_fields")
    val mandatoryFields: Map<String, String?>,
    @SerializedName("dynamic_fields")
    val dynamicFields: Map<String, String?>
)