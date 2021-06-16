package com.apiumhub.vyou_core.login.vyou_auth

import com.apiumhub.vyou_core.domain.VYouException

sealed class VYouAuthException(override val message: String? = null): VYouException(message ?: "Unknown error") {
    object InvalidRedirectUri: VYouAuthException("Error retrieving parameter code from redirectUri")
    object NoConnection: VYouAuthException("No internet connection")
}
