package com.apiumhub.vyou_ui.profile.presentation

import android.os.Parcelable
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TenantCompliant(
    val tenantDataCompliant: Boolean,
    val tenantConsentCompliant: Boolean
) : Parcelable {
    constructor(credentials: VyouCredentials) : this(
        credentials.tenantCompliant,
        credentials.tenantConsentCompliant
    )

    @IgnoredOnParcel
    val isBothCompliant
        get() = tenantDataCompliant && tenantConsentCompliant
}