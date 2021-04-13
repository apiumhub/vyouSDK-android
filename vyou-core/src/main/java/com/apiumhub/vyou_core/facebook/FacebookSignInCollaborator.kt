package com.apiumhub.vyou_core.facebook

import android.content.Intent
import androidx.fragment.app.Fragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.channels.Channel

internal class FacebookSignInCollaborator {
    private val fbLoginManager = LoginManager.getInstance()
    private val callbackManager = CallbackManager.Factory.create()
    private val resultChannel = Channel<String>()

    suspend fun start(fragment: Fragment): String {
        fbLoginManager.registerCallback(callbackManager, MyFbCallback())
        fbLoginManager.logInWithReadPermissions(fragment, listOf("public_profile", "email"))
        return resultChannel.receive()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private inner class MyFbCallback: FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            result?.accessToken?.let {
                resultChannel.offer(it.token)
            }
        }

        override fun onCancel() {
            resultChannel.close()
        }

        override fun onError(error: FacebookException?) {
            resultChannel.close(error)
        }
    }
}