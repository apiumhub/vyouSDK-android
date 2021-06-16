package com.apiumhub.vyou_core.login.facebook

import com.apiumhub.vyou_core.domain.VYouException

sealed class VYouFacebookException(override val message: String? = null): VYouException(message ?: "Unknown Error") {
    object Cancel: VYouFacebookException("Facebook login cancelled by user")
    data class Error(override val message: String?): VYouFacebookException(message ?: "There was an unknown error trying to authenticate user with facebook")
}
