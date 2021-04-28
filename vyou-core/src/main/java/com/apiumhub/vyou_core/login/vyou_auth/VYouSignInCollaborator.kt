package com.apiumhub.vyou_core.login.vyou_auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.domain.VYouResult
import kotlinx.coroutines.channels.Channel

class VYouSignInCollaborator(actResultCaller: ActivityResultCaller) {
    private val resultChannel = Channel<VYouResult<VyouAuthResponse>>()

    private val vyouAuthLauncher: ActivityResultLauncher<Unit> = actResultCaller
        .registerForActivityResult(getContract()) {
            resultChannel.offer(it)
        }

    suspend fun start(): VYouResult<VyouAuthResponse> {
        vyouAuthLauncher.launch(Unit)
        return resultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<Unit, VYouResult<VyouAuthResponse>>() {
        override fun createIntent(context: Context, input: Unit): Intent = AuthWebviewActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?): VYouResult<VyouAuthResponse> =
            if (intent?.getStringExtra("code") == null || intent.getStringExtra("code_verifier") == null)
                VYouResult.Failure(IllegalStateException("Error retrieving parameter code from redirectUri"))
        else {
            VYouResult.Success(VyouAuthResponse(intent.getStringExtra("code")!!, intent.getStringExtra("code_verifier")!!))
            }
    }
}

data class VyouAuthResponse(
    val code: String,
    val codeVerifier: String
)