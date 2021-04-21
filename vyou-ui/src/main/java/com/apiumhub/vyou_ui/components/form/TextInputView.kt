package com.apiumhub.vyou_ui.components.form

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouTextInputBinding
import com.apiumhub.vyou_ui.extensions.addLeftIconToTextField
import com.apiumhub.vyou_ui.register.domain.TextField
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.exception.ValidationException

internal fun TextInputView(context: Context, inputField: TextField) =
    TextInputView(context).apply { render(inputField) }

internal class TextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

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

    override fun setValue(value: String) {
        binding.textInputEt.setText(value)
    }

    override fun getKeyValue(): FieldOutModel? =
        binding.textInputEt.text?.takeIf { it.isNotEmpty() }?.let {
            FieldOutModel(inputField.fieldType, inputField.id, it.toString())
        }

    fun render(inputField: TextField) {
        this.inputField = inputField
        tag = inputField.id
        binding.inputLayout.hint = inputField.title

        binding.textInputEt.inputType = when (inputField.inputType) {
            TextField.VyouInputType.TEXT -> InputType.TYPE_CLASS_TEXT
            TextField.VyouInputType.NUMBER -> InputType.TYPE_CLASS_NUMBER
            TextField.VyouInputType.EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        addLeftIconToTextField(
            inputField.isRequired,
            binding.inputLayout,
            R.drawable.ic_mandatory_field
        )
    }

    override fun validate() = apply {
        binding.textInputEt.text?.let {
            if (inputField.isRequired && it.isEmpty()) {
                binding.inputLayout.error = "Field is mandatory"
                throw ValidationException(this)
            }
            if (it.isNotEmpty() && inputField.inputType == TextField.VyouInputType.EMAIL && !Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                binding.inputLayout.error = "Invalid E-mail"
                throw ValidationException(this)
            }
            binding.inputLayout.error = null
        }
    }
}