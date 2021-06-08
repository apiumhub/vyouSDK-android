package com.apiumhub.vyou_ui.components.checkboxes

import android.content.Context
import com.apiumhub.vyou_ui.R

internal class CheckBoxField(val id: String, val isRequired: Boolean, val url: String){
    fun getTitle(context: Context) = when(id) {
        "privacy_policy" -> context.getString(R.string.checkbox_privacy_policy_text) to context.getString(R.string.checkbox_privacy_policy_link)
        "terms_conditions" -> context.getString(R.string.checkbox_terms_and_conditions_text) to context.getString(R.string.checkbox_terms_and_conditions_link)
        "comercial_info" -> context.getString(R.string.checkbox_commercial_info_text) to context.getString(R.string.checkbox_commercial_info_link)
        else -> id to ""
    }

    fun getError(context: Context) = when(id) {
        "privacy_policy" -> context.getString(R.string.error_checkbox_privacy_policy)
        "terms_conditions" -> context.getString(R.string.error_checkbox_terms_and_conditions)
        else -> id
    }
}