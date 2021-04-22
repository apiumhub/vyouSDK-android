package com.apiumhub.vyou_core.login.facebook

import android.content.Intent
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_core.domain.VyouResult
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.channels.Channel

internal class FacebookSignInCollaborator {
    private val fbLoginManager = LoginManager.getInstance()
    private val callbackManager = CallbackManager.Factory.create()
    private val resultChannel = Channel<VyouResult<String>>()

    suspend fun start(fragment: Fragment): VyouResult<String> {
        fbLoginManager.registerCallback(callbackManager, MyFbCallback())
        fbLoginManager.logInWithReadPermissions(fragment, listOf("public_profile", "email"))
        return resultChannel.receive()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private inner class MyFbCallback : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            result?.accessToken?.let {
                resultChannel.offer(VyouResult.Success(it.token))
            }
        }

        override fun onCancel() {
            resultChannel.offer(VyouResult.Failure(IllegalStateException("Facebook login cancelled by user")))
        }

        override fun onError(error: FacebookException?) {
            resultChannel.offer(
                VyouResult.Failure(
                    error ?: IllegalStateException("There was an unknown error trying to authenticate user with facebook")
                )
            )
        }
    }
}