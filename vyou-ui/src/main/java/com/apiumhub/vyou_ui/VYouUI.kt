package com.apiumhub.vyou_ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.VYou
import com.apiumhub.vyou_core.domain.VYouException
import com.apiumhub.vyou_core.domain.VYouResult
import com.apiumhub.vyou_core.domain.VYouResult.Failure
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.login.data.CredentialsStorage
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_ui.profile.di.profileModule
import com.apiumhub.vyou_ui.profile.presentation.VYouProfileActivity
import com.apiumhub.vyou_ui.register.di.registerModule
import com.apiumhub.vyou_ui.register.presentation.VYouRegisterActivity
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.channels.Channel
import org.koin.core.module.Module

class VYouUI(activityResultCaller: ActivityResultCaller) {

    private val registerCollaborator = RegisterCollaborator(activityResultCaller)
    private val profileCollaborator = ProfileCollaborator(activityResultCaller)

    suspend fun startRegister() = registerCollaborator.start()

    suspend fun startProfile(credentials: VYouCredentials) = profileCollaborator.start(credentials)

    companion object {
        fun initialize(application: Application, additionalModules: List<Module> = emptyList()) {
            AndroidThreeTen.init(application)
            VYou.initialize(
                application,
                listOf(
                    registerModule,
                    profileModule
                ) + additionalModules
            )
        }
    }
}

internal class RegisterCollaborator(activityResultCaller: ActivityResultCaller) {
    private val registerResultChannel = Channel<VYouResult<Unit>>()

    private val registerLauncher: ActivityResultLauncher<Unit> = activityResultCaller
        .registerForActivityResult(getContract()) {
            registerResultChannel.offer(it)
        }

    suspend fun start(): VYouResult<Unit> {
        registerLauncher.launch(Unit)
        return registerResultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<Unit, VYouResult<Unit>>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            VYouRegisterActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            when (resultCode) {
                Activity.RESULT_OK -> Success(Unit)
                else -> Failure(VYouException("Error during register"))
            }
    }
}

internal class ProfileCollaborator(activityResultCaller: ActivityResultCaller) {
    private val profileResultChannel = Channel<VYouResult<Unit>>()

    private val profileLauncher: ActivityResultLauncher<VYouCredentials> = activityResultCaller
        .registerForActivityResult(getContract()) {
            profileResultChannel.offer(it)
        }

    suspend fun start(credentials: VYouCredentials): VYouResult<Unit> {
        profileLauncher.launch(credentials)
        return profileResultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<VYouCredentials, VYouResult<Unit>>() {
        override fun createIntent(context: Context, input: VYouCredentials): Intent =
            VYouProfileActivity.getCallingIntent(context, input)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            when (resultCode) {
                Activity.RESULT_OK -> Success(Unit)
                else -> Failure(VYouException("Error editing user's profile"))
            }
    }
}