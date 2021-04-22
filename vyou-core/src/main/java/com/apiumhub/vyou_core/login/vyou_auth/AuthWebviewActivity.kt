package com.apiumhub.vyou_core.login.vyou_auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.apiumhub.vyou_core.R
import com.apiumhub.vyou_core.data.ManifestReader
import org.koin.android.ext.android.inject

internal class AuthWebviewActivity : AppCompatActivity(R.layout.activity_auth_webview) {
    private val manifestReader: ManifestReader by inject()
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

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
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