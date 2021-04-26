package com.apiumhub.vyou_core.login.vyou_auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.domain.VYouResult
import kotlinx.coroutines.channels.Channel

class VYouSignInCollaborator(actResultCaller: ActivityResultCaller) {
    private val resultChannel = Channel<VYouResult<String>>()

    private val vyouAuthLauncher: ActivityResultLauncher<Unit> = actResultCaller
        .registerForActivityResult(getContract()) {
            resultChannel.offer(it)
        }

    suspend fun start(): VYouResult<String> {
        vyouAuthLauncher.launch(Unit)
        return resultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<Unit, VYouResult<String>>() {
        override fun createIntent(context: Context, input: Unit): Intent = AuthWebviewActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?): VYouResult<String> =
            intent?.getStringExtra("code")?.let {
                VYouResult.Success(it)
            } ?: VYouResult.Failure(IllegalStateException("Error retrieving parameter code from redirectUri"))
    }
}