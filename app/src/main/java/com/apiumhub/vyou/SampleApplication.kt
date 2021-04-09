package com.apiumhub.vyou

import android.app.Application
import com.apiumhub.vyou_core.VyouCore

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        VyouCore.initialize(this)
    }
}