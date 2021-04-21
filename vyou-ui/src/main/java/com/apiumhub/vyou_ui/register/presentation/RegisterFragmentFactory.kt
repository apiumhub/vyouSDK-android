package com.apiumhub.vyou_ui.register.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class RegisterFragmentFactory(
    private val onSuccess: () -> Unit,
    private val onError: (error: Throwable) -> Unit
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return VyouRegisterFragment(onSuccess, onError)
    }
}