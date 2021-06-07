package com.apiumhub.vyou_ui.components.form

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apiumhub.vyou_core.session.domain.VYouProfile
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.register.domain.*

internal class DynamicFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    val isValid get() = children.map { it as VYouInputComponent }.all { it.isValid }

    fun render(fields: List<InputField>) {
        fields
            .map { createComponent(it) }
            .forEach { addView(it) }
    }

    fun observe(onChange: () -> Unit) {
        children.map { it as VYouInputComponent }.forEach {  component ->
            component.observe { onChange() }
        }
    }

    fun getResponses(): List<FieldOutModel> =
        children
            .map { (it as VYouInputComponent).validate().getKeyValue() }
            .filterNotNull()
            .toList()

    private fun createComponent(it: InputField): View =
        when (it) {
            is DateField -> DateInputView(context, it)
            is DropdownField -> DropdownInputView(context, it)
            is RadioGroupField -> RadioGroupInputView(context, it)
            is TextField -> TextInputView(context, it)
            is PasswordField -> PasswordInputView(context, it)
        }

    fun fillWithProfile(profile: VYouProfile) {
        val components = children.map { it as VYouInputComponent }.groupBy { it.id }
        profile.email?.let {
            components["vyou_internal_email"]?.first()?.setValue(it)
        }
        profile.dynamicFields.entries.forEach { entry ->
            entry.value?.let {
                if (it.isNotEmpty() && it.isNotBlank())
                    components[entry.key]?.first()?.setValue(it)
            }
        }
        profile.mandatoryFields.entries.forEach { entry ->
            entry.value?.let {
                if (it.isNotEmpty() && it.isNotBlank())
                    components[entry.key]?.first()?.setValue(it)
            }
        }
        removeView(findViewWithTag("vyou_internal_password"))
    }
}