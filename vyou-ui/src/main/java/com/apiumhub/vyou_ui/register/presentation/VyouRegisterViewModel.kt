package com.apiumhub.vyou_ui.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.tenant.VyouTenantManager
import com.apiumhub.vyou_core.tenant.domain.CreateCustomerDto
import com.apiumhub.vyou_ui.register.domain.UiTenant
import com.apiumhub.vyou_ui.register.ui.FieldOutModel
import com.apiumhub.vyou_ui.register.ui.FieldType
import kotlinx.coroutines.launch

internal class VyouRegisterViewModel(private val tenantManager: VyouTenantManager) : ViewModel() {

    private val _dynamicForm = MutableLiveData<UiTenant>()
    val dynamicForm: LiveData<UiTenant> = _dynamicForm

    init {
        viewModelScope.launch {
            _dynamicForm.value = UiTenant(tenantManager.tenant())
        }
    }

    fun sendDataToRegister(customer: Map<FieldType, List<FieldOutModel>>, checkboxes: List<Pair<String, Boolean>>) {
        viewModelScope.launch {
            tenantManager.register(
                CreateCustomerDto(
                    email = customer.getValue(FieldType.EMAIL).first().value,
                    password = customer.getValue(FieldType.PASSWORD).first().value,
                    customFields = customer[FieldType.CUSTOM]?.associate { it.key to it.value } ?: emptyMap(),
                    defaultFields = customer[FieldType.DEFAULT]?.associate { it.key to it.value } ?: emptyMap(),
                    tenantRoles = emptyList(),
                    infoAccepted = checkboxes.first { it.first == "comercial_info" }.second,
                    privacyAccepted = checkboxes.first { it.first == "privacy_policy" }.second,
                    termsConditionsAccepted = checkboxes.first { it.first == "terms_conditions" }.second
                )
            )
        }
    }
}