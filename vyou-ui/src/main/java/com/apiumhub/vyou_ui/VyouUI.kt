package com.apiumhub.vyou_ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_ui.profile.di.profileModule
import com.apiumhub.vyou_ui.profile.presentation.VyouProfileActivity
import com.apiumhub.vyou_ui.register.di.registerModule
import com.apiumhub.vyou_ui.register.presentation.VyouRegisterActivity
import com.jakewharton.threetenabp.AndroidThreeTen

object VyouUI {

    fun initialize(application: Application) {
        AndroidThreeTen.init(application)
        Vyou.initialize(application, listOf(registerModule, profileModule))
    }

    suspend fun startRegister(activity: Activity) {
        activity.startActivity(VyouRegisterActivity.getCallingIntent(activity))
    }

    suspend fun startProfile(activity: Activity, credentials: VyouCredentials) {
        activity.startActivity(VyouProfileActivity.getCallingIntent(activity, credentials))
    }
}