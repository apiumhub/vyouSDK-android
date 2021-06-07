package com.apiumhub.vyou_ui.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.domain.VYouResult.Failure
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.tenant.VYouTenantManager
import com.apiumhub.vyou_core.tenant.domain.RegisterDto
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.FieldType
import com.apiumhub.vyou_ui.register.domain.UiTenant
import kotlinx.coroutines.launch

internal class VYouRegisterViewModel(private val genderList: List<String>, private val tenantManager: VYouTenantManager) : ViewModel() {

    private val _dynamicForm = MutableLiveData<UiTenant>()
    val dynamicForm: LiveData<UiTenant> = _dynamicForm

    private val _userRegistered = MutableLiveData<Unit>()
    val userRegistered: LiveData<Unit> = _userRegistered

    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    init {
        viewModelScope.launch {
            when (val result = tenantManager.tenant()) {
                is Success -> _dynamicForm.value = UiTenant(result.value, genderList)
                is Failure -> _errorLiveData.value = result.error
            }
        }
    }

    fun sendDataToRegister(
        customer: Map<FieldType, List<FieldOutModel>>,
        checkboxes: List<Pair<String, Boolean>>
    ) {
        viewModelScope.launch {
            when (val result = tryRegister(customer, checkboxes)) {
                is Failure -> _errorLiveData.value = result.error
                is Success -> _userRegistered.value = Unit
            }
        }
    }

    private suspend fun tryRegister(
        customer: Map<FieldType, List<FieldOutModel>>,
        checkboxes: List<Pair<String, Boolean>>
    ) =
        tenantManager.register(
            RegisterDto(
                email = customer.getValue(FieldType.EMAIL).first().value,
                password = customer.getValue(FieldType.PASSWORD).first().value,
                dynamicFields = customer[FieldType.CUSTOM]?.associate { it.key to it.value } ?: emptyMap(),
                mandatoryFields = customer[FieldType.DEFAULT]?.associate { it.key to it.value } ?: emptyMap(),
                tenantRoles = listOf("CUSTOMER"),
                infoAccepted = checkboxes.first { it.first == "comercial_info" }.second,
                privacyAccepted = checkboxes.first { it.first == "privacy_policy" }.second,
                termsConditionsAccepted = checkboxes.first { it.first == "terms_conditions" }.second
            )
        )
}