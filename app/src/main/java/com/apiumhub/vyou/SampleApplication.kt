package com.apiumhub.vyou

import android.app.Application
import com.apiumhub.vyou_core.Vyou

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Vyou.initialize(this)
    }
}