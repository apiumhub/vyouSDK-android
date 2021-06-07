package com.apiumhub.vyou_ui.profile.di

import com.apiumhub.vyou_ui.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel {
        ProfileViewModel(it[0])
    }
}