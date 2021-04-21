package com.apiumhub.vyou_ui.profile.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TenantCompliant(
    val tenantDataCompliant: Boolean,
    val tenantConsentCompliant: Boolean
): Parcelable