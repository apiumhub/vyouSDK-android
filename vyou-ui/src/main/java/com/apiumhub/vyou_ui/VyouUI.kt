package com.apiumhub.vyou_ui

import android.app.Application
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_ui.register.di.registerModule
import com.apiumhub.vyou_ui.register.presentation.VyouRegisterFragment

object VyouUI {
    val registerFragment: VyouRegisterFragment
        get() = VyouRegisterFragment.newInstance()

    fun initialize(application: Application) {
        Vyou.initialize(application, listOf(registerModule))
    }

}