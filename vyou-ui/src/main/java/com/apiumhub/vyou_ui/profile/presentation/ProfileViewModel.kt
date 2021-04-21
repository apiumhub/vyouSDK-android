package com.apiumhub.vyou_ui.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.domain.VyouResult.Failure
import com.apiumhub.vyou_core.domain.VyouResult.Success
import com.apiumhub.vyou_core.session.data.EditProfileDto
import com.apiumhub.vyou_core.session.domain.VyouProfile
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.FieldType
import com.apiumhub.vyou_ui.register.domain.UiTenant
import kotlinx.coroutines.launch

internal class ProfileViewModel : ViewModel() {

    private val _tenant = MutableLiveData<UiTenant>()
    val tenant: LiveData<UiTenant> = _tenant

    private val _profile = MutableLiveData<VyouProfile>()
    val profile: LiveData<VyouProfile> = _profile

    private val _saved = MutableLiveData<Unit>()
    val saved: LiveData<Unit> = _saved

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    init {
        viewModelScope.launch {
            Vyou.session?.let {
                when (val tenantResult = Vyou.tenantManager.tenant()) {
                    is Success -> _tenant.value = UiTenant(tenantResult.value)
                    is Failure -> _error.value = tenantResult.error
                }
                when (val profileResult = it.tenantProfile()) {
                    is Success -> _profile.value = profileResult.value
                    is Failure -> _error.value = profileResult.error
                }
            }
        }
    }

    fun saveData(
        customer: Map<FieldType, List<FieldOutModel>>,
        checkboxes: List<Pair<String, Boolean>>
    ) {
        viewModelScope.launch {
            when (val result = Vyou.session?.editProfile(createDto(customer, checkboxes))) {
                is Success -> _saved.value = Unit
                is Failure -> _error.value = result.error
            }
        }
    }

    private fun createDto(customer: Map<FieldType, List<FieldOutModel>>, checkboxes: List<Pair<String, Boolean>>) = EditProfileDto(
        email = customer.getValue(FieldType.EMAIL).first().value,
        infoAccepted = checkboxes.first { it.first == "comercial_info" }.second,
        privacyAccepted = checkboxes.first { it.first == "privacy_policy" }.second,
        termsConditionsAccepted = checkboxes.first { it.first == "terms_conditions" }.second,
        dynamicFields = customer[FieldType.CUSTOM]?.associate { it.key to it.value } ?: emptyMap(),
        mandatoryFields = customer[FieldType.DEFAULT]?.associate { it.key to it.value } ?: emptyMap()
    )
}