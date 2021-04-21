package com.apiumhub.vyou_core.session.data

import com.google.gson.annotations.SerializedName

data class EditProfileDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("info_accepted")
    val infoAccepted: Boolean,
    @SerializedName("privacy_accepted")
    val privacyAccepted: Boolean,
    @SerializedName("terms_conditions_accepted")
    val termsConditionsAccepted: Boolean,
    @SerializedName("dynamic_fields")
    val dynamicFields: Map<String, String>,
    @SerializedName("mandatory_fields")
    val mandatoryFields: Map<String, String>
)