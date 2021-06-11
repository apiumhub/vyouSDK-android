package com.apiumhub.vyou_ui.register.domain

import android.content.Context
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.FieldType

internal sealed class InputField(val id: String, val isRequired: Boolean, val fieldType: FieldType, val readOnly: Boolean = false) {
    fun getTitle(context: Context) =
        when (id) {
            "birth" -> context.getString(R.string.field_birthdate_label)
            "country" -> context.getString(R.string.field_country_label)
            "name" -> context.getString(R.string.field_name_label)
            "phone" -> context.getString(R.string.field_phone_label)
            "gender" -> context.getString(R.string.field_gender_label)
            "surname" -> context.getString(R.string.field_surname_label)
            "vyou_internal_email" -> context.getString(R.string.field_email_label)
            "vyou_internal_password" -> context.getString(R.string.field_password_label)
            else -> id.lowercase().replaceFirstChar { it.uppercase() }
        }
}

internal class TextField(id: String, isRequired: Boolean, val inputType: VYouInputType, fieldType: FieldType, readOnly: Boolean) : InputField(id, isRequired, fieldType, readOnly) {

    enum class VYouInputType(val type: String) {
        TEXT("string"),
        NUMBER("number"),
        PHONE("phone"),
        EMAIL("email");

        companion object {
            fun fromType(type: String) = values().find { it.type == type } ?: TEXT
            fun fromName(name: String, type: String) = values().find { name.contains(it.type, true) } ?: fromType(type)
        }
    }
}

internal class PasswordField(id: String) : InputField(id, true, FieldType.PASSWORD)

internal class DateField(id: String, isRequired: Boolean, fieldType: FieldType) : InputField(id, isRequired, fieldType)

internal class DropdownField(id: String, isRequired: Boolean, val options: List<String>, fieldType: FieldType) : InputField(id, isRequired, fieldType)

internal class RadioGroupField(id: String, isRequired: Boolean, val options: List<String>, fieldType: FieldType) : InputField(id, isRequired, fieldType)
