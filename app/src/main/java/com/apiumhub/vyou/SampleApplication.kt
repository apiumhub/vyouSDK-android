package com.apiumhub.vyou

import android.app.Application
import com.apiumhub.vyou_ui.VyouUI

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VyouUI.initialize(this)
    }
}