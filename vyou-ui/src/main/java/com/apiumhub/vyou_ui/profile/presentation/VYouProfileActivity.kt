package com.apiumhub.vyou_ui.profile.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import com.apiumhub.vyou_ui.R

class VYouProfileActivity : AppCompatActivity(R.layout.vyou_profile_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tenantCompliant = intent?.extras?.getParcelable<TenantCompliant>("tenantCompliant") ?: throw IllegalArgumentException("Tenant compliant argument is mandatory for Profile activity")
        (supportFragmentManager.findFragmentByTag("profileFragmentTag") as? VYouProfileFragment)?.let {
            it.tenantCompliant = tenantCompliant
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        fun getCallingIntent(context: Context, credentials: VYouCredentials): Intent =
            Intent(context, VYouProfileActivity::class.java)
                .apply {
                    putExtras(bundleOf("tenantCompliant" to TenantCompliant(credentials)))
                }
    }

}