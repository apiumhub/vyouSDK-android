package com.apiumhub.vyou_core.login.vyou_auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.apiumhub.vyou_core.R
import com.apiumhub.vyou_core.data.ManifestReader
import org.koin.android.ext.android.inject
import java.security.MessageDigest
import java.security.SecureRandom

internal class AuthWebviewActivity : AppCompatActivity(R.layout.activity_auth_webview) {
    private val manifestReader: ManifestReader by inject()
    private val clientId by lazy { manifestReader.readVYouClientId() }
    private val redirectUri by lazy { manifestReader.readVYouRedirectUri() }
    private val vyouUrl by lazy { manifestReader.readVYouUrl() }

    val codeVerifier: String by lazy {
        val sha1Random = SecureRandom.getInstance("SHA1PRNG")
        sha1Random.setSeed(sha1Random.generateSeed(8))
        val values = ByteArray(32)
        sha1Random.nextBytes(values) // SHA1PRNG, seeded properly
        Base64.encodeToString(values, Base64.URL_SAFE + Base64.NO_WRAP)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView: WebView = findViewById(R.id.authWebview)
        webView.webViewClient = VYouWebviewClient(this)
        webView.loadUrl(
            "$vyouUrl/oauth/authorize?" +
                    "client_id=$clientId" +
                    "&response_type=code" +
                    "&redirect_uri=$redirectUri" +
                    "&code_challenge=${createCodeChallenge(codeVerifier)}" +
                    "&code_challenge_method=S256"
        )
    }

    private fun createCodeChallenge(verifier: String): String {
        val bytes = verifier.toByteArray(Charsets.US_ASCII)
        val md = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest = md.digest()
        return Base64.encodeToString(digest, Base64.URL_SAFE + Base64.NO_WRAP)
    }

    private inner class VYouWebviewClient(private val activity: Activity) : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if (url != null && url.startsWith(redirectUri)) {
                val code = url.substringAfter("code=")
                activity.setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra("code", code)
                    putExtra("code_verifier", codeVerifier)
                })
                activity.finish()
            }
        }
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, AuthWebviewActivity::class.java)
    }
}