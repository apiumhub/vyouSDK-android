package com.apiumhub.vyou_ui.components.form

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.FieldType
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouPasswordInputBinding
import com.apiumhub.vyou_ui.register.domain.PasswordField

internal fun PasswordInputView(context: Context, inputField: PasswordField) =
    PasswordInputView(context).apply { render(inputField) }

internal class PasswordInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VYouInputComponent {

    private val binding: VyouPasswordInputBinding =
        VyouPasswordInputBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var inputField: PasswordField
    override val id: String
        get() = inputField.id

    override val isValid: Boolean
        get() = inputField.isRequired || (binding.passwordInputEt.text.toString().isNotEmpty()
                && binding.repeatPasswordEt.text.toString().isNotEmpty()
                && binding.passwordInputEt.text.toString() == binding.repeatPasswordEt.text.toString())

    override var visible: Boolean
        get() = isVisible
        set(value) {
            isVisible = value
        }

    override fun setValue(value: String) {
        //NoOp
    }

    override fun getKeyValue(): FieldOutModel? = binding.passwordInputEt.text?.let {
        FieldOutModel(FieldType.PASSWORD, inputField.id, it.toString())
    }

    override fun validate(): VYouInputComponent = apply {
        val password = binding.passwordInputEt.text ?: throw ValidationException(this)
        val repeatedPassword = binding.repeatPasswordEt.text ?: throw ValidationException(this)
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = context.getString(R.string.error_field_is_mandatory)
            throw ValidationException(this)
        } else
            binding.passwordInputLayout.error = null
        if (repeatedPassword.isEmpty()) {
            binding.repeatPasswordInputLayout.error = context.getString(R.string.error_field_is_mandatory)
            throw ValidationException(this)
        } else
            binding.repeatPasswordInputLayout.error = null
        if (password.toString() != repeatedPassword.toString()) {
            binding.passwordInputLayout.error = null
            binding.repeatPasswordInputLayout.error = context.getString(R.string.error_field_password_doesnt_matches)
            throw ValidationException(this)
        }
        binding.passwordInputLayout.error = null
        binding.repeatPasswordInputLayout.error = null
    }

    fun render(inputField: PasswordField) {
        this.inputField = inputField
        tag = inputField.id
        binding.passwordInputLayout.hint = context.getString(R.string.field_password_label)
        binding.repeatPasswordInputLayout.hint = context.getString(R.string.field_repeat_password_label)

        binding.repeatPasswordEt.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) runCatching { validate() }
        }
    }

    override fun observe(onChange: () -> Unit) {
        binding.passwordInputEt.addTextChangedListener {
            onChange()
        }

        binding.repeatPasswordEt.addTextChangedListener {
            onChange()
        }
    }
}