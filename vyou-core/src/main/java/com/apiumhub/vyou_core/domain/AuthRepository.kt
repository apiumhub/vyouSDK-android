package com.apiumhub.vyou_core.domain

import com.apiumhub.vyou_core.VyouCredentials

interface AuthRepository {
    suspend fun authenticateWithVyouCode(code: String): VyouCredentials
}