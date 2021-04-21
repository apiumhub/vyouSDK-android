package com.apiumhub.vyou_ui.profile.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class ProfileFragmentFactory(
    private val onSuccess: () -> Unit,
    private val onError: (error: Throwable) -> Unit,
    private val tenantCompliant: TenantCompliant
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return VyouProfileFragment(onSuccess, onError, tenantCompliant)
    }
}