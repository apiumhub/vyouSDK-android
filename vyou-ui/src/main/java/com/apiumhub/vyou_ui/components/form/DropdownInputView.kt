package com.apiumhub.vyou_ui.components.form

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouDropdownInputBinding
import com.apiumhub.vyou_ui.register.domain.DropdownField

internal fun DropdownInputView(context: Context, inputField: DropdownField) =
    DropdownInputView(context).apply { render(inputField) }

internal class DropdownInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VYouInputComponent {

    private val binding: VyouDropdownInputBinding =
        VyouDropdownInputBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var inputField: DropdownField

    override val id: String
        get() = inputField.id

    override val isValid: Boolean
        get() = inputField.isRequired || binding.dropTextInputEt.text.toString().isNotEmpty()

    override var visible: Boolean
        get() = isVisible
        set(value) {
            isVisible = value
        }

    override fun setValue(value: String) {
        binding.dropTextInputEt.setText(value)
    }

    override fun getKeyValue(): FieldOutModel? =
        binding.dropTextInputEt.text?.takeIf { it.isNotEmpty() }?.let {
            FieldOutModel(inputField.fieldType, inputField.id, it.toString())
        }

    fun render(inputField: DropdownField) {
        this.inputField = inputField
        tag = inputField.id
        binding.dropInputLayout.hint = inputField.getTitle(context)

        binding.dropTextInputEt.setAdapter(ArrayAdapter(context, R.layout.vyou_dropdown_item, inputField.options))
        if(inputField.isRequired.not()) {
            binding.dropInputLayout.hint = inputField.getTitle(context) + " (${context.getString(R.string.field_optional)})"
        }

        binding.dropTextInputEt.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) runCatching { validate() }
        }
    }

    override fun validate() = apply {

        if (inputField.isRequired && binding.dropTextInputEt.text.isEmpty()) {
            binding.dropInputLayout.error = context.getString(R.string.error_field_is_mandatory)
            throw ValidationException(this)
        }
        binding.dropInputLayout.error = null
    }

    override fun observe(onChange: () -> Unit) {
        binding.dropTextInputEt.addTextChangedListener {
            onChange()
        }
    }
}