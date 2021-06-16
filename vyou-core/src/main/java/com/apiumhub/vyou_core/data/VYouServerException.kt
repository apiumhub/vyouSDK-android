package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.domain.VYouException
import java.net.HttpURLConnection

sealed class VYouServerException(message: String? = null): VYouException(message ?: "Unknown server error") {
    object NotAuthorized: VYouServerException("Server - Not authorized")
    object Forbidden: VYouServerException("Server - Forbidden")
    object NotFound: VYouServerException("Server - Not Found")
    object General: VYouServerException("Server - General")

    companion object {
        fun fromCode(code: Int): VYouServerException = when(code) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> NotAuthorized
            HttpURLConnection.HTTP_FORBIDDEN -> Forbidden
            HttpURLConnection.HTTP_NOT_FOUND -> NotFound
            else -> General
        }
    }
}
