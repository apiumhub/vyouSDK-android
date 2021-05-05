package com.apiumhub.vyou_ui.register.domain

import com.apiumhub.vyou_core.tenant.domain.VYouFieldDto
import com.apiumhub.vyou_core.tenant.domain.VYouTenant
import com.apiumhub.vyou_ui.components.FieldType
import com.apiumhub.vyou_ui.components.checkboxes.CheckBoxField

internal class UiTenant(from: VYouTenant) {
    val fields: List<InputField>
    val checkBoxes: List<CheckBoxField> = mapCheckBoxes(from)

    init {
        fields = listOf(
            TextField("vyou_internal_email", true, TextField.VYouInputType.EMAIL, FieldType.EMAIL),
            *mapFields(from.mandatoryFields, FieldType.DEFAULT),
            *mapFields(from.dynamicFields, FieldType.CUSTOM),
            PasswordField("vyou_internal_password")
        )
    }

    private fun mapFields(from: List<VYouFieldDto>, fieldType: FieldType) =
        from
            .sortedBy { it.order }
            .map {
                when (it.name) {
                    "birth" -> DateField("birth", it.required, fieldType)
                    "gender" -> RadioGroupField(
                        "gender",
                        it.required,
                        listOf("Male", "Female"),
                        fieldType
                    )
                    else -> TextField(
                        it.name,
                        it.required,
                        TextField.VYouInputType.fromType(it.type),
                        fieldType
                    )
                }
            }.toTypedArray()

    private fun mapCheckBoxes(from: VYouTenant): List<CheckBoxField> {
        val listCheckBoxes = mutableListOf(
            CheckBoxField("privacy_policy", true, from.privacyUrl),
            CheckBoxField("terms_conditions", true, from.termsConditionsUrl)
        )
        from.infoUrl?.let {
            listCheckBoxes.add(CheckBoxField("comercial_info", false, it))
        }
        return listCheckBoxes
    }
}