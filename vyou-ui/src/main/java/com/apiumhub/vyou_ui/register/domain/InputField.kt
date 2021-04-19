package com.apiumhub.vyou_ui.register.domain

import com.apiumhub.vyou_ui.register.ui.FieldType

internal sealed class InputField(val id: String, val isRequired: Boolean, val fieldType: FieldType) {
    val title = when(id){
        "birth" -> "Fecha de Nacimiento"
        "country" -> "País"
        "name" -> "Nombre"
        "phone" -> "Teléfono"
        "gender" -> "Género"
        "surname" -> "Apellido"
        "vyou_internal_email" -> "Email"
        "vyou_internal_password" -> "Contraseña"
        "vyou_internal_repeat_password" -> "Repetir contraseña"
        else -> id.capitalize()
    }
}

internal class TextField(id: String, isRequired: Boolean, val inputType: VyouInputType, fieldType: FieldType) : InputField(id, isRequired, fieldType) {

    enum class VyouInputType(val type: String) {
        TEXT("string"),
        NUMBER("number"),
        EMAIL("email"),
        PASSWORD("password");

        companion object {
            fun fromType(type: String) = values().find { it.type == type } ?: TEXT
        }
    }
}

internal class DateField(id: String, isRequired: Boolean, fieldType: FieldType) : InputField(id, isRequired, fieldType)

internal class RadioGroupField(id: String, isRequired: Boolean, val options: List<String>, fieldType: FieldType) : InputField(id, isRequired, fieldType)
