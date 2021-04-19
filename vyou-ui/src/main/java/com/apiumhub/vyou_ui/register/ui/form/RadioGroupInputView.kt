package com.apiumhub.vyou_ui.register.ui.form

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.core.view.isVisible
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouRadioGroupInputBinding
import com.apiumhub.vyou_ui.extensions.addLeftIconToTextField
import com.apiumhub.vyou_ui.register.domain.RadioGroupField
import com.apiumhub.vyou_ui.register.ui.FieldOutModel
import com.apiumhub.vyou_ui.register.ui.exception.ValidationException

internal fun RadioGroupInputView(context: Context, inputfield: RadioGroupField) =
    RadioGroupInputView(context).apply { render(inputfield) }

internal class RadioGroupInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding =
        VyouRadioGroupInputBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var inputField: RadioGroupField

    fun render(inputField: RadioGroupField) {
        this.inputField = inputField
        tag = inputField.id
        binding.titleRadioGroup.text = inputField.title
        addLeftIconToTextField(
            inputField.isRequired,
            binding.titleRadioGroup,
            R.drawable.ic_mandatory_field
        )
        inputField.options
            .mapIndexed(::mapToRadioButton)
            .forEach(binding.radioGroup::addView)
    }

    private fun mapToRadioButton(index: Int, field: String) =
        RadioButton(context).also { radioButton ->
            radioButton.text = field
            radioButton.id = index
        }

    override fun getKeyValue(): FieldOutModel?=
        binding.radioGroup.checkedRadioButton?.text?.let {
            FieldOutModel(inputField.fieldType, inputField.id, it.toString())
        }

    private val RadioGroup.checkedRadioButton
        get() = runCatching { get(checkedRadioButtonId) as RadioButton }.getOrNull()

    override fun validate() = apply {
        if (inputField.isRequired && binding.radioGroup.checkedRadioButton == null) {
            binding.radioGroupError.isVisible = true
            throw ValidationException(this)
        } else
            binding.radioGroupError.isVisible = false

    }
}