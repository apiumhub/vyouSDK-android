package com.apiumhub.vyou_core.auth

import android.util.Base64
import okhttp3.Interceptor
import java.nio.charset.StandardCharsets

internal class AuthInterceptor(private val base64Encoder: Base64Encoder) : Interceptor {

    override fun intercept(chain: Interceptor.Chain) =
        chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Basic ${base64Encoder.vyouClientIdEncodedForAuth}")
            .build()
            .let(chain::proceed)
}

class Base64Encoder(vyouClientId: String) {
    val vyouClientIdEncodedForAuth: String =
        Base64.encodeToString("$vyouClientId:$vyouClientId".toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
}