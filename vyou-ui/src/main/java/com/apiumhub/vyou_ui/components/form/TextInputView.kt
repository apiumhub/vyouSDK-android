package com.apiumhub.vyou_ui.components.form

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouTextInputBinding
import com.apiumhub.vyou_ui.register.domain.TextField

internal fun TextInputView(context: Context, inputField: TextField) =
    TextInputView(context).apply { render(inputField) }

internal class TextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VYouInputComponent {

    private val binding: VyouTextInputBinding =
        VyouTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var inputField: TextField

    override val id: String
        get() = inputField.id

    override var visible: Boolean
        get() = isVisible
        set(value) {
            isVisible = value
        }

    override val isValid: Boolean
        get() {
            return inputField.isRequired.not() || (binding.textInputEt.text.toString().isNotEmpty() && when (inputField.inputType) {
                TextField.VYouInputType.EMAIL -> Patterns.EMAIL_ADDRESS.matcher(binding.textInputEt.text.toString()).matches()
                TextField.VYouInputType.PHONE -> Patterns.PHONE.matcher(binding.textInputEt.text.toString()).matches()
                else -> true
            })
        }

    override fun setValue(value: String) {
        binding.textInputEt.setText(value)
    }

    override fun getKeyValue(): FieldOutModel? =
        binding.textInputEt.text?.takeIf { inputField.isRequired.not() || it.isNotEmpty() }?.let {
            FieldOutModel(inputField.fieldType, inputField.id, it.toString())
        }

    fun render(inputField: TextField) {
        this.inputField = inputField
        tag = inputField.id
        binding.inputLayout.hint = inputField.getTitle(context)

        binding.textInputEt.inputType = when (inputField.inputType) {
            TextField.VYouInputType.TEXT -> InputType.TYPE_CLASS_TEXT
            TextField.VYouInputType.NUMBER -> InputType.TYPE_CLASS_NUMBER
            TextField.VYouInputType.PHONE -> InputType.TYPE_CLASS_PHONE
            TextField.VYouInputType.EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        if (inputField.isRequired.not()) {
            binding.inputLayout.hint = inputField.getTitle(context) + " (${context.getString(R.string.field_optional)})"
        }

        binding.textInputEt.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) runCatching { validate() }
        }

        binding.inputLayout.isEnabled = inputField.readOnly.not()
    }

    override fun validate() = apply {
        binding.textInputEt.text?.let {
            if (inputField.isRequired && it.isEmpty()) {
                binding.inputLayout.error = context.getString(R.string.error_field_is_mandatory)
                throw ValidationException(this)
            }
            if (it.isNotEmpty() && inputField.inputType == TextField.VYouInputType.EMAIL && !Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                binding.inputLayout.error = context.getString(R.string.error_field_password_invalid_email)
                throw ValidationException(this)
            }
            if (it.isNotEmpty() && inputField.inputType == TextField.VYouInputType.PHONE && !Patterns.PHONE.matcher(it).matches()) {
                binding.inputLayout.error = context.getString(R.string.error_field_password_invalid_phone_number)
                throw ValidationException(this)
            }
            binding.inputLayout.error = null
        }
    }

    override fun observe(onChange: () -> Unit) {
        binding.textInputEt.addTextChangedListener { text ->
            onChange()
        }
    }
}