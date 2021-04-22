package com.apiumhub.vyou_ui

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_ui.profile.di.profileModule
import com.apiumhub.vyou_ui.profile.presentation.VyouProfileActivity
import com.apiumhub.vyou_ui.register.di.registerModule
import com.apiumhub.vyou_ui.register.presentation.VyouRegisterActivity
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.channels.Channel

class VyouUI(activityResultCaller: ActivityResultCaller) {

    private val registerCollaborator = RegisterCollaborator(activityResultCaller)
    private val profileCollaborator = ProfileCollaborator(activityResultCaller)

    suspend fun startRegister() {
        registerCollaborator.start()
    }

    suspend fun startProfile(credentials: VyouCredentials) {
        profileCollaborator.start(credentials)
    }

    companion object {
        fun initialize(application: Application) {
            AndroidThreeTen.init(application)
            Vyou.initialize(application, listOf(registerModule, profileModule))
        }
    }
}

internal class RegisterCollaborator(activityResultCaller: ActivityResultCaller) {
    private val registerResultChannel = Channel<Unit>()

    private val registerLauncher: ActivityResultLauncher<Unit> = activityResultCaller
        .registerForActivityResult(getContract()) {
            registerResultChannel.offer(Unit)
        }

    suspend fun start() {
        registerLauncher.launch(Unit)
        return registerResultChannel.receive()
    }

    private fun getContract(): ActivityResultContract<Unit, Unit> = object : ActivityResultContract<Unit, Unit>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            VyouRegisterActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?) {
            //TODO
        }
    }
}

internal class ProfileCollaborator(activityResultCaller: ActivityResultCaller) {
    private val profileResultChannel = Channel<Unit>()

    private val profileLauncher: ActivityResultLauncher<VyouCredentials> = activityResultCaller
        .registerForActivityResult(getContract()) {
            profileResultChannel.offer(Unit)
        }

    suspend fun start(credentials: VyouCredentials) {
        profileLauncher.launch(credentials)
        return profileResultChannel.receive()
    }

    private fun getContract(): ActivityResultContract<VyouCredentials, Unit> = object : ActivityResultContract<VyouCredentials, Unit>() {
        override fun createIntent(context: Context, input: VyouCredentials): Intent =
            VyouProfileActivity.getCallingIntent(context, input)

        override fun parseResult(resultCode: Int, intent: Intent?) {
            //TODO
        }
    }

}