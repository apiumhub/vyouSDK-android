package com.apiumhub.vyou_ui.register.di

import com.apiumhub.vyou_core.VYou
import com.apiumhub.vyou_ui.register.presentation.VYouRegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerModule = module {
    viewModel {
        VYouRegisterViewModel(it[0], VYou.tenantManager)
    }
}