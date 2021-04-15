package com.apiumhub.vyou_ui.register.domain

import com.apiumhub.vyou_core.tenant.domain.VyouTenant

internal class UiTenant(from: VyouTenant) {
    val fields: List<InputField>

    init {
        fields = listOf(
            TextField("vyou_internal_email", TextField.VyouInputType.EMAIL),
            *mapMandatoryFields(from),
            *mapMandatoryFields(from),
            TextField("vyou_internal_password", TextField.VyouInputType.PASSWORD),
            TextField("vyou_internal_repeat_password", TextField.VyouInputType.PASSWORD)
        )
    }

    private fun mapMandatoryFields(from: VyouTenant) = from
        .dynamicFields
        .map {
            when (it.name) {
                "birth" -> DateField("birth")
                "gender" -> RadioButtonField("gender", listOf("Male", "Female"))
                else -> TextField(it.name, TextField.VyouInputType.fromType(it.type))
            }
        }
        .toTypedArray()
}
/*
email
mandatory fields
dynamic fields
password
repeat password
 */
