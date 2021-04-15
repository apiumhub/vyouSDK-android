package com.apiumhub.vyou_ui.register.di

import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_ui.register.presentation.VyouRegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerModule = module {
    viewModel {
        VyouRegisterViewModel(Vyou.tenantManager)
    }
}