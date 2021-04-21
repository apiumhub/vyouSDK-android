package com.apiumhub.vyou_ui

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_ui.profile.di.profileModule
import com.apiumhub.vyou_ui.profile.presentation.VyouProfileFragment
import com.apiumhub.vyou_ui.profile.presentation.ProfileFragmentFactory
import com.apiumhub.vyou_ui.profile.presentation.TenantCompliant
import com.apiumhub.vyou_ui.register.di.registerModule
import com.apiumhub.vyou_ui.register.presentation.RegisterFragmentFactory
import com.apiumhub.vyou_ui.register.presentation.VyouRegisterFragment
import com.jakewharton.threetenabp.AndroidThreeTen

object VyouUI {

    fun initialize(application: Application) {
        AndroidThreeTen.init(application)
        Vyou.initialize(application, listOf(registerModule, profileModule))
    }

    fun getProfileFragment(
        fragmentManager: FragmentManager,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
        credentials: VyouCredentials
    ): Fragment {
        fragmentManager.fragmentFactory = ProfileFragmentFactory(onSuccess, onError, TenantCompliant(credentials))
        return fragmentManager.fragmentFactory.instantiate(ClassLoader.getSystemClassLoader(), VyouProfileFragment::class.java.name)
    }

    fun getRegisterFragment(
        fragmentManager: FragmentManager,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ): Fragment {
        fragmentManager.fragmentFactory = RegisterFragmentFactory(onSuccess, onError)
        return fragmentManager.fragmentFactory.instantiate(ClassLoader.getSystemClassLoader(), VyouRegisterFragment::class.java.name)
    }
}