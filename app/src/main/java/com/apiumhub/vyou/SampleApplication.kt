package com.apiumhub.vyou

import android.app.Application
import com.apiumhub.vyou_ui.VYouUI

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VYouUI.initialize(this)
    }
}