package com.apiumhub.vyou_core.google

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.data.ManifestReader
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.channels.Channel

class GoogleSignInCollaborator(activityResultCaller: ActivityResultCaller, context: Context, application: Application) {

    private val gso = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(ManifestReader(application).readGoogleClientId())
        .requestEmail()
        .build()

    private val signInClient = GoogleSignIn.getClient(context, gso)

    private val resultChannel = Channel<String>()

    private val googleSignInLauncher: ActivityResultLauncher<Unit> = activityResultCaller
        .registerForActivityResult(getContract()) {
            resultChannel.offer(it)
        }

    suspend fun start(): String {
        googleSignInLauncher.launch(Unit)
        return resultChannel.receive()
    }

    private fun getContract(): ActivityResultContract<Unit, String> = object : ActivityResultContract<Unit, String>() {
        override fun createIntent(context: Context, input: Unit): Intent = signInClient.signInIntent

        override fun parseResult(resultCode: Int, intent: Intent?) =
            GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException::class.java)?.idToken
    }
}