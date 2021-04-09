package com.apiumhub.vyou_core.data

import android.content.Context
import com.apiumhub.vyou_core.VyouCredentials
import com.apiumhub.vyou_core.auth.ManifestReader
import com.apiumhub.vyou_core.domain.AuthRepository

internal class AuthRetrofitRepository(private val vyouApi: VyouApi, private val context: Context) : AuthRepository {
    override suspend fun authenticateWithVyouCode(code: String): VyouCredentials =
        vyouApi.webAccessToken(code, ManifestReader.readVyouRedirectUri(context))
}