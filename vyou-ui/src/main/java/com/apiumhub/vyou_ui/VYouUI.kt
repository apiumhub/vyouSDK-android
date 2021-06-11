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
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_ui.edit_profile.di.editProfileModule
import com.apiumhub.vyou_ui.edit_profile.presentation.VYouEditProfileActivity
import com.apiumhub.vyou_ui.profile.di.profileModule
import com.apiumhub.vyou_ui.profile.presentation.VYouProfileActivity
import com.apiumhub.vyou_ui.register.di.registerModule
import com.apiumhub.vyou_ui.register.presentation.VYouRegisterActivity
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.channels.Channel
import org.koin.core.module.Module

class VYouUI(activityResultCaller: ActivityResultCaller) {

    private val registerCollaborator = Collaborator<Unit>(activityResultCaller) { context, _ ->
        VYouRegisterActivity.getCallingIntent(context)
    }
    private val profileCollaborator = Collaborator<VYouCredentials>(activityResultCaller) { context, input ->
        VYouProfileActivity.getCallingIntent(context, input)
    }
    private val editProfileCollaborator = Collaborator<VYouCredentials>(activityResultCaller) { context, input ->
        VYouEditProfileActivity.getCallingIntent(context, input)
    }

    suspend fun startRegister() = registerCollaborator.start()

    suspend fun startProfile(credentials: VYouCredentials) = profileCollaborator.start(credentials)

    suspend fun startEditProfile(credentials: VYouCredentials) = editProfileCollaborator.start(credentials)

    companion object {
        fun initialize(application: Application, additionalModules: List<Module> = emptyList()) {
            AndroidThreeTen.init(application)
            VYou.initialize(
                application,
                listOf(
                    registerModule,
                    profileModule,
                    editProfileModule
                ) + additionalModules
            )
        }
    }
}

private class Collaborator<T>(activityResultCaller: ActivityResultCaller, private val intent: (Context, T) -> Intent) {
    private val resultChannel = Channel<VYouResult<Unit>>()

    private val resultLauncher: ActivityResultLauncher<T> = activityResultCaller
        .registerForActivityResult(getContract()) {
            resultChannel.offer(it)
        }

    suspend fun start(credentials: T = Unit as T): VYouResult<Unit> {
        resultLauncher.launch(credentials)
        return resultChannel.receive()
    }

    private fun getContract() = object : ActivityResultContract<T, VYouResult<Unit>>() {
        override fun createIntent(context: Context, input: T): Intent = intent.invoke(context, input)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            when (resultCode) {
                Activity.RESULT_OK -> Success(Unit)
                else -> Failure(VYouException("Error invoking: $intent"))
            }
    }
}