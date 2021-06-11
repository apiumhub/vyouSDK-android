package com.apiumhub.vyou_ui.profile.presentation

import androidx.activity.result.ActivityResultCaller
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiumhub.vyou_core.VYou
import com.apiumhub.vyou_core.domain.VYouResult.Failure
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.session.data.EditProfileDto
import com.apiumhub.vyou_core.session.domain.VYouProfile
import com.apiumhub.vyou_ui.VYouUI
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.FieldType
import com.apiumhub.vyou_ui.register.domain.UiTenant
import kotlinx.coroutines.launch

internal class VYouProfileViewModel(private val genderList: List<String>) : ViewModel() {

    private val _tenant = MutableLiveData<UiTenant>()
    val tenant: LiveData<UiTenant> = _tenant

    private val _profile = MutableLiveData<VYouProfile>()
    val profile: LiveData<VYouProfile> = _profile

    private val _logOut = MutableLiveData<Unit>()
    val logOut: LiveData<Unit> = _logOut

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    init {
        viewModelScope.launch {
            VYou.session?.let {
                when (val tenantResult = VYou.tenantManager.tenant()) {
                    is Success -> _tenant.value = UiTenant(tenantResult.value, genderList)
                    is Failure -> _error.value = tenantResult.error
                }
            }
        }
        onViewCreated()
    }

    fun onViewCreated() {
        viewModelScope.launch {
            VYou.session?.let {
                when (val profileResult = it.tenantProfile()) {
                    is Success -> _profile.value = profileResult.value
                    is Failure -> _error.value = profileResult.error
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            VYou.session?.let {
                when (val result = it.signOut()) {
                    is Success -> _logOut.value = result.value
                    is Failure -> _error.value = result.error
                }
            }
        }
    }
}