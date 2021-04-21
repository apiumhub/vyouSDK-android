package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.di.Base64Encoder
import okhttp3.Interceptor
import okhttp3.Response

class ClientCredentialsInterceptor(private val base64Encoder: Base64Encoder) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .addHeader("Client-Credentials", "Basic ${base64Encoder.vyouClientIdEncodedForAuth}")
            .build()
            .let(chain::proceed)
}