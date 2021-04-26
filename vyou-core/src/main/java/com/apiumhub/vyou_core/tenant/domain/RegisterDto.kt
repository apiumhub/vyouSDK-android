package com.apiumhub.vyou_core.tenant.domain

import com.google.gson.annotations.SerializedName

data class RegisterDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("info_accepted")
    val infoAccepted: Boolean,
    @SerializedName("privacy_accepted")
    val privacyAccepted: Boolean,
    @SerializedName("terms_conditions_accepted")
    val termsConditionsAccepted: Boolean,
    @SerializedName("tenant_roles")
    val tenantRoles: List<String>,
    @SerializedName("dynamic_fields")
    val dynamicFields: Map<String, String>,
    @SerializedName("mandatory_fields")
    val mandatoryFields: Map<String, String>
)