package com.apiumhub.vyou_core.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.apiumhub.vyou_core.R

internal class AuthWebviewActivity : AppCompatActivity(R.layout.activity_auth_webview) {

    private val clientId = "8XxbGzbBAAbapQ9tppEaJvNa8jzKDw9h0KBWQYvT2P67T42lAlrZrG7KWg"
    private val redirectUri = "vyouapp://com.vyouapp.auth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView: WebView = findViewById(R.id.authWebview)
        webView.webViewClient = VyouWebviewClient(this)
        webView.loadUrl("https://test.vyou-app.com:8380/oauth/authorize?client_id=$clientId&response_type=code&redirect_uri=$redirectUri")
    }

    private class VyouWebviewClient(private val activity: Activity) : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            url?.let {
                if (it.startsWith("vyouapp://com.vyouapp.auth")) {
                    val code = it.substringAfter("code=")
                    activity.setResult(Activity.RESULT_OK, Intent().apply { putExtra("code", code) })
                    activity.finish()
                }
            }
            activity.setResult(Activity.RESULT_CANCELED)
            activity.finish()
        }
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, AuthWebviewActivity::class.java)
    }
}