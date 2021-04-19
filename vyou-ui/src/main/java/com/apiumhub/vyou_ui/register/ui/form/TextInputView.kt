package com.apiumhub.vyou_ui.register.ui.form

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouTextInputBinding
import com.apiumhub.vyou_ui.extensions.addLeftIconToTextField
import com.apiumhub.vyou_ui.register.domain.TextField
import com.apiumhub.vyou_ui.register.ui.exception.ValidationException

internal fun TextInputView(context: Context, inputField: TextField) =
    TextInputView(context).apply { render(inputField) }

internal class TextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding: VyouTextInputBinding =
        VyouTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    override fun getKeyValue(): Pair<String, String> =
        tag.toString() to binding.textInputEt.text.toString()

    private lateinit var inputField: TextField

    fun render(inputField: TextField) {
        this.inputField = inputField
        tag = inputField.id
        binding.inputLayout.hint = inputField.title

        binding.textInputEt.inputType = when (inputField.inputType) {
            TextField.VyouInputType.TEXT -> InputType.TYPE_CLASS_TEXT
            TextField.VyouInputType.NUMBER -> InputType.TYPE_CLASS_NUMBER
            TextField.VyouInputType.EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            TextField.VyouInputType.PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        if (inputField.inputType == TextField.VyouInputType.PASSWORD) {
            binding.textInputEt.transformationMethod = PasswordTransformationMethod.getInstance()
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
            if (inputField.inputType == TextField.VyouInputType.EMAIL && !Patterns.EMAIL_ADDRESS.matcher(it).matches()){
                binding.inputLayout.error = "Invalid E-mail"
                throw ValidationException(this)
            }
            binding.inputLayout.error = null
        }
    }
}