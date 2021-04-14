package com.apiumhub.vyou_core.login.vyou_auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.apiumhub.vyou_core.R
import com.apiumhub.vyou_core.data.ManifestReader

internal class AuthWebviewActivity(private val manifestReader: ManifestReader) : AppCompatActivity(R.layout.activity_auth_webview) {
    private val clientId by lazy { manifestReader.readVyouClientId() }
    private val redirectUri by lazy { manifestReader.readVyouRedirectUri() }
    private val vyouUrl by lazy { manifestReader.readVyouUrl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView: WebView = findViewById(R.id.authWebview)
        webView.webViewClient = VyouWebviewClient(this)
        webView.loadUrl("$vyouUrl/oauth/authorize?client_id=$clientId&response_type=code&redirect_uri=$redirectUri")
    }

    private inner class VyouWebviewClient(private val activity: Activity) : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (url != null && url.startsWith(redirectUri)) {
                val code = url.substringAfter("code=")
                activity.setResult(Activity.RESULT_OK, Intent().apply { putExtra("code", code) })
                activity.finish()
            }
        }
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, AuthWebviewActivity::class.java)
    }
}