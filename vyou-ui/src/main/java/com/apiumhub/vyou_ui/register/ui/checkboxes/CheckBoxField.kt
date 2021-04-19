package com.apiumhub.vyou_ui.register.ui.checkboxes

internal class CheckBoxField(id: String, val isRequired: Boolean, val url: String){
    val title : Pair<String, String> = when(id){
        "privacy_policy" -> "He leido y acepto la política de privacidad" to "política de privacidad"
        "terms_conditions" -> "He leído y acepto los terminos y condiciones de uso" to "terminos y condiciones de uso"
        "comercial_info" -> "Me gustaría recibir información comercial" to "información comercial"
        else -> id to ""
    }
}