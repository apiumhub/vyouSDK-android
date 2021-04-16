package com.apiumhub.vyou_ui.register.domain

import java.util.*

internal sealed class InputField(val id: String) {
    open fun isValid() = true
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

internal class TextField(id: String, val inputType: VyouInputType, val regex: String? = null) : InputField(id) {

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

internal class DateField(id: String) : InputField(id) {

}

internal class RadioGroupField(id: String, val options: List<String>) : InputField(id) {

}
