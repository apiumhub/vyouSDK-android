package com.apiumhub.vyou_ui.register.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.apiumhub.vyou_core.domain.VyouException
import com.apiumhub.vyou_core.domain.VyouResult
import com.apiumhub.vyou_ui.R

class VyouRegisterActivity : AppCompatActivity(R.layout.vyou_register_activity) {

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        fun getCallingIntent(context: Context) = Intent(context, VyouRegisterActivity::class.java)
    }
}