package com.apiumhub.vyou_ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.domain.VyouException
import com.apiumhub.vyou_core.domain.VyouResult
import com.apiumhub.vyou_core.domain.VyouResult.Failure
import com.apiumhub.vyou_core.domain.VyouResult.Success
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

    suspend fun startRegister() = registerCollaborator.start()

    suspend fun startProfile(credentials: VyouCredentials) = profileCollaborator.start(credentials)

    companion object {
        fun initialize(application: Application) {
            AndroidThreeTen.init(application)
            Vyou.initialize(application, listOf(registerModule, profileModule))
        }
    }
}

internal class RegisterCollaborator(activityResultCaller: ActivityResultCaller) {
    private val registerResultChannel = Channel<VyouResult<Unit>>()

    private val registerLauncher: ActivityResultLauncher<Unit> = activityResultCaller
        .registerForActivityResult(getContract()) {
            registerResultChannel.offer(it)
        }

    suspend fun start(): VyouResult<Unit> {
        registerLauncher.launch(Unit)
        return registerResultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<Unit, VyouResult<Unit>>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            VyouRegisterActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            when (resultCode) {
                Activity.RESULT_OK -> Success(Unit)
                else -> Failure(VyouException("Error during register"))
            }
    }
}

internal class ProfileCollaborator(activityResultCaller: ActivityResultCaller) {
    private val profileResultChannel = Channel<VyouResult<Unit>>()

    private val profileLauncher: ActivityResultLauncher<VyouCredentials> = activityResultCaller
        .registerForActivityResult(getContract()) {
            profileResultChannel.offer(it)
        }

    suspend fun start(credentials: VyouCredentials): VyouResult<Unit> {
        profileLauncher.launch(credentials)
        return profileResultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<VyouCredentials, VyouResult<Unit>>() {
        override fun createIntent(context: Context, input: VyouCredentials): Intent =
            VyouProfileActivity.getCallingIntent(context, input)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            when (resultCode) {
                Activity.RESULT_OK -> Success(Unit)
                else -> Failure(VyouException("Error editing user's profile"))
            }
    }
}