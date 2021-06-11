package com.apiumhub.vyou_ui.edit_profile.di

import com.apiumhub.vyou_ui.edit_profile.presentation.VYouEditProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editProfileModule = module {
    viewModel {
        VYouEditProfileViewModel(it[0])
    }
}