package com.apiumhub.vyou_ui.register.ui.form

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.databinding.VyouPasswordInputBinding
import com.apiumhub.vyou_ui.register.domain.PasswordField
import com.apiumhub.vyou_ui.register.ui.FieldOutModel
import com.apiumhub.vyou_ui.register.ui.FieldType
import com.apiumhub.vyou_ui.register.ui.exception.ValidationException

internal fun PasswordInputView(context: Context, inputField: PasswordField) =
    PasswordInputView(context).apply { render(inputField) }

internal class PasswordInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding: VyouPasswordInputBinding =
        VyouPasswordInputBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var inputField: PasswordField

    override fun getKeyValue(): FieldOutModel? = binding.passwordInputEt.text?.let {
        FieldOutModel(FieldType.PASSWORD, inputField.id, it.toString())
    }

    override fun validate(): VyouInputComponent = apply {
        val password = binding.passwordInputEt.text ?: throw ValidationException(this)
        val repeatedPassword = binding.repeatPasswordEt.text ?: throw ValidationException(this)
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "This field is mandatory"
            throw ValidationException(this)
        } else
            binding.passwordInputLayout.error = null
        if (repeatedPassword.isEmpty()) {
            binding.repeatPasswordInputLayout.error = "This field is mandatory"
            throw ValidationException(this)
        } else
            binding.repeatPasswordInputLayout.error = null
        if (password.toString() != repeatedPassword.toString()) {
            binding.passwordInputLayout.error = null
            binding.repeatPasswordInputLayout.error = "Passwords don't match"
            throw ValidationException(this)
        }
        binding.passwordInputLayout.error = null
        binding.repeatPasswordInputLayout.error = null
    }

    fun render(inputField: PasswordField) {
        this.inputField = inputField
        tag = inputField.id
        binding.passwordInputLayout.hint = "Type your password"
        binding.repeatPasswordInputLayout.hint = "Repeat your password"
    }
}