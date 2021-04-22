package com.apiumhub.vyou_ui.register.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.apiumhub.vyou_ui.R

class VyouRegisterActivity : AppCompatActivity(R.layout.vyou_register_activity) {
    companion object {
        fun getCallingIntent(context: Context) = Intent(context, VyouRegisterActivity::class.java)
    }
}