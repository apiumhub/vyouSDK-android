package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.session.domain.SessionRepository
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizationInterceptor: Interceptor, KoinComponent {

    private val sessionRepository: SessionRepository by inject()

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .apply {
                sessionRepository.getSession()?.credentials?.accessToken?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
            .build()
            .let(chain::proceed)
}