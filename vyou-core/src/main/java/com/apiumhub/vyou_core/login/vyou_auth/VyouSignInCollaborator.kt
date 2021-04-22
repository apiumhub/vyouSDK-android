package com.apiumhub.vyou_core.login.vyou_auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.domain.VyouResult
import kotlinx.coroutines.channels.Channel
import java.lang.IllegalStateException

class VyouSignInCollaborator(actResultCaller: ActivityResultCaller) {
    private val resultChannel = Channel<VyouResult<String>>()

    private val vyouAuthLauncher: ActivityResultLauncher<Unit> = actResultCaller
        .registerForActivityResult(getContract()) {
            resultChannel.offer(it)
        }

    suspend fun start(): VyouResult<String> {
        vyouAuthLauncher.launch(Unit)
        return resultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<Unit, VyouResult<String>>() {
        override fun createIntent(context: Context, input: Unit): Intent = AuthWebviewActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?): VyouResult<String> =
            intent?.getStringExtra("code")?.let {
                    VyouResult.Success(it)
            } ?: VyouResult.Failure(IllegalStateException("Error retrieving parameter code from redirectUri"))
    }
}