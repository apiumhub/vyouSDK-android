package com.apiumhub.vyou_core.domain

import com.google.gson.annotations.SerializedName

data class VyouTenant(
    @SerializedName("admin")
    val admin: String,
    @SerializedName("allowed_roles")
    val allowedRoles: List<String>,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("privacy_url")
    val privacyUrl: String,
    @SerializedName("redirect_uris")
    val redirectUris: List<String>,
    @SerializedName("terms_conditions_url")
    val termsConditionsUrl: String,
    @SerializedName("stripe")
    val stripe: Boolean?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("info_url")
    val infoUrl: String?,
    @SerializedName("one_signal")
    val oneSignal: Boolean?,
    @SerializedName("mandatory_fields")
    val mandatoryFields: List<VyouFieldDto>,
    @SerializedName("dynamic_fields")
    val dynamicFields: List<VyouFieldDto>
)

data class VyouFieldDto(
    val name: String,
    val required: Boolean,
    val type: String
)