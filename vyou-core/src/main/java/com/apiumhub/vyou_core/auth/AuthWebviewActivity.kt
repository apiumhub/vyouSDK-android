package com.apiumhub.vyou_core.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.apiumhub.vyou_core.R

class AuthWebviewActivity : AppCompatActivity(R.layout.activity_auth_webview) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView: WebView = findViewById(R.id.authWebview)
        webView.loadUrl("http://www.google.es")
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, AuthWebviewActivity::class.java)
    }
}