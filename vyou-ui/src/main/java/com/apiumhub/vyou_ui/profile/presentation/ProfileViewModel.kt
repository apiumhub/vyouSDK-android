package com.apiumhub.vyou_ui.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.domain.VyouResult.Failure
import com.apiumhub.vyou_core.domain.VyouResult.Success
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
        fields: Map<FieldType, List<FieldOutModel>>,
        checkboxes: List<Pair<String, Boolean>>
    ) {
        TODO("Not yet implemented")
    }


}