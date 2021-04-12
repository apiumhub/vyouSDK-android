package com.apiumhub.vyou_core.facebook

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

internal class FacebookSignInCollaborator(activityResultCaller: ActivityResultCaller, context: Context) {
    private val fbLoginManager = LoginManager.getInstance()
    private val callbackManager = CallbackManager.Factory.create()
    private val tokenTracker = MyAccessTokenTracker()


    suspend fun start(fragment: Fragment): String {
        tokenTracker.startTracking()
        fbLoginManager.logInWithReadPermissions(fragment, listOf("public_profile"))
        fbLoginManager.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
            }
        })
        return ""
    }

    private inner class MyAccessTokenTracker : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
            Log.d("Token", "old: $oldAccessToken new $currentAccessToken")
        }
    }
}