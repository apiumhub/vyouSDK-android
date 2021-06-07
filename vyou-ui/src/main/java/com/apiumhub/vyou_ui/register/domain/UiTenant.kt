package com.apiumhub.vyou_ui.register.domain

import com.apiumhub.vyou_core.tenant.domain.VYouFieldDto
import com.apiumhub.vyou_core.tenant.domain.VYouTenant
import com.apiumhub.vyou_ui.components.FieldType
import com.apiumhub.vyou_ui.components.checkboxes.CheckBoxField

internal class UiTenant(from: VYouTenant, private val genderList: List<String>) {
    val fields: List<InputField>
    val checkBoxes: List<CheckBoxField> = mapCheckBoxes(from)

    init {
        fields = listOf(
            TextField("vyou_internal_email", true, TextField.VYouInputType.EMAIL, FieldType.EMAIL),
            PasswordField("vyou_internal_password"),
            *mapFields(from.mandatoryFields, FieldType.DEFAULT),
            *mapFields(from.dynamicFields, FieldType.CUSTOM)
        )
    }

    private fun mapFields(from: List<VYouFieldDto>, fieldType: FieldType) =
        from
            .sortedBy { it.order }
            .map {
                when (it.name) {
                    "birth" -> DateField("birth", it.required, fieldType)
                    "gender" -> DropdownField(
                        "gender",
                        it.required,
                        genderList,
                        fieldType
                    )
                    else -> TextField(
                        it.name,
                        it.required,
                        TextField.VYouInputType.fromName(it.name, it.type),
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