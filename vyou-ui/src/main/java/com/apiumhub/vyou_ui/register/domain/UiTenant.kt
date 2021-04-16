package com.apiumhub.vyou_ui.register.domain

import com.apiumhub.vyou_core.tenant.domain.VyouFieldDto
import com.apiumhub.vyou_core.tenant.domain.VyouTenant
import com.apiumhub.vyou_ui.register.ui.checkboxes.CheckBoxField

internal class UiTenant(from: VyouTenant) {
    val fields: List<InputField>
    val checkBoxes : List<CheckBoxField> = mapCheckBoxes(from)

    init {
        fields = listOf(
            TextField("vyou_internal_email", TextField.VyouInputType.EMAIL),
            *mapFields(from.mandatoryFields),
            *mapFields(from.dynamicFields),
            TextField("vyou_internal_password", TextField.VyouInputType.PASSWORD),
            TextField("vyou_internal_repeat_password", TextField.VyouInputType.PASSWORD))
    }

    private fun mapFields(from: List<VyouFieldDto>) =
        from
            .map {
                when (it.name) {
                    "birth" -> DateField("birth")
                    "gender" -> RadioGroupField("gender", listOf("Male", "Female"))
                    else -> TextField(it.name, TextField.VyouInputType.fromType(it.type))
                }
            }
            .toTypedArray()

    private fun mapCheckBoxes(from: VyouTenant): List<CheckBoxField>{
        val listCheckBoxes = mutableListOf(
            CheckBoxField("privacy_policy", from.privacyUrl),
            CheckBoxField("terms_conditions", from.termsConditionsUrl)
        )
        from.infoUrl?.let {
            listCheckBoxes.add(CheckBoxField("comercial_info", it))
        }
        return listCheckBoxes
    }
}