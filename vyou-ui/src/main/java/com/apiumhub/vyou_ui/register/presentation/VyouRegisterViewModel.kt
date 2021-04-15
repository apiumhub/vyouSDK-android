package com.apiumhub.vyou_ui.register.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.tenant.VyouTenantManager
import kotlinx.coroutines.launch

internal class VyouRegisterViewModel(private val tenantManager: VyouTenantManager) : ViewModel() {
    fun start() {
        viewModelScope.launch {
            Log.d("Tenant", "${tenantManager.tenant()}")
        }
    }
}