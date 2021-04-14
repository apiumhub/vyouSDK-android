package com.apiumhub.vyou_core.login.vyou_auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.channels.Channel

class VyouSignInCollaborator(actResultCaller: ActivityResultCaller) {
    private val resultChannel = Channel<String>()

    private val vyouAuthLauncher: ActivityResultLauncher<Unit> = actResultCaller
        .registerForActivityResult(getContract()) {
            resultChannel.offer(it)
        }

    suspend fun start(): String {
        vyouAuthLauncher.launch(Unit)
        return resultChannel.receive()
    }

    private fun getContract(): ActivityResultContract<Unit, String> = object : ActivityResultContract<Unit, String>() {
        override fun createIntent(context: Context, input: Unit): Intent = AuthWebviewActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            intent?.let {
                intent.getStringExtra("code")
            }
    }
}