package com.apiumhub.vyou_ui.register.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.apiumhub.vyou_ui.R

class VYouRegisterActivity : AppCompatActivity(R.layout.vyou_register_activity) {

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        fun getCallingIntent(context: Context) = Intent(context, VYouRegisterActivity::class.java)
    }
}