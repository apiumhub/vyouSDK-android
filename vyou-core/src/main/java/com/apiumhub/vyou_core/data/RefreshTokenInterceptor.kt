package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.session.domain.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.HttpURLConnection

internal class RefreshTokenInterceptor : Interceptor, KoinComponent {

    private val sessionRepository: SessionRepository by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            request.headers["Authorization"]?.let {
                runBlocking {
                    runCatching {
                        val newRequest = chain
                            .request()
                            .newBuilder()
                            .header("Authorization", "Bearer ${sessionRepository.refreshToken().accessToken}")
                            .build()
                        chain.proceed(newRequest)
                    }.getOrElse { response }
                }
            } ?: response
        } else
            response
    }
}