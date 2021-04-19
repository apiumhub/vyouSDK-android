package com.apiumhub.vyou_ui.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.tenant.VyouTenantManager
import com.apiumhub.vyou_core.tenant.domain.CreateCustomerDto
import com.apiumhub.vyou_ui.register.domain.UiTenant
import kotlinx.coroutines.launch

internal class VyouRegisterViewModel(private val tenantManager: VyouTenantManager) : ViewModel() {

    private val _dynamicForm = MutableLiveData<UiTenant>()
    val dynamicForm: LiveData<UiTenant> = _dynamicForm

    init {
        viewModelScope.launch {
            _dynamicForm.value = UiTenant(tenantManager.tenant())
        }
    }

    fun sendDataToRegister(customer: CreateCustomerDto){
        viewModelScope.launch {
            tenantManager.register(customer)
        }

    }
}