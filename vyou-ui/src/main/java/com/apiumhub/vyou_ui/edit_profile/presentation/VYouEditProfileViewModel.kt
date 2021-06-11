package com.apiumhub.vyou_ui.edit_profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.VYou
import com.apiumhub.vyou_core.domain.VYouResult.Failure
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.session.data.EditProfileDto
import com.apiumhub.vyou_core.session.domain.VYouProfile
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.FieldType
import com.apiumhub.vyou_ui.register.domain.UiTenant
import kotlinx.coroutines.launch

internal class VYouEditProfileViewModel(private val genderList: List<String>) : ViewModel() {

    private val _tenant = MutableLiveData<UiTenant>()
    val tenant: LiveData<UiTenant> = _tenant

    private val _profile = MutableLiveData<VYouProfile>()
    val profile: LiveData<VYouProfile> = _profile

    private val _saved = MutableLiveData<Unit>()
    val saved: LiveData<Unit> = _saved

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    init {
        viewModelScope.launch {
            VYou.session?.let {
                when (val tenantResult = VYou.tenantManager.tenant()) {
                    is Success -> _tenant.value = UiTenant(tenantResult.value, genderList, listOf("vyou_internal_email"))
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
            when (val result = VYou.session?.editProfile(createDto(customer, checkboxes))) {
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