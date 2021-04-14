package com.apiumhub.vyou_core.di

import android.util.Base64
import java.nio.charset.StandardCharsets

class Base64Encoder(vyouClientId: String) {
    val vyouClientIdEncodedForAuth: String =
        Base64.encodeToString("$vyouClientId:$vyouClientId".toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
}