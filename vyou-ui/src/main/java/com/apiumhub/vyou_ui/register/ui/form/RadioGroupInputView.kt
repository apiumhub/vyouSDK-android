package com.apiumhub.vyou_ui.register.ui.form

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouRadioGroupInputBinding
import com.apiumhub.vyou_ui.extensions.addLeftIconToTextField
import com.apiumhub.vyou_ui.register.domain.RadioGroupField

internal fun RadioGroupInputView(context: Context, inputfield: RadioGroupField) = RadioGroupInputView(context).apply { render(inputfield) }

internal class RadioGroupInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding = VyouRadioGroupInputBinding.inflate(LayoutInflater.from(context), this, true)

    fun render(inputField: RadioGroupField) {
        tag = inputField.id
        binding.titleRadioGroup.text = inputField.title
        addLeftIconToTextField(inputField.isMandatory, binding.titleRadioGroup, R.drawable.ic_mandatory_field)
        inputField.options
            .mapIndexed(::mapToRadioButton)
            .forEach(binding.radioGroup::addView)
    }

    private fun mapToRadioButton(index: Int, field: String) =
        RadioButton(context).also { radioButton ->
            radioButton.text = field
            radioButton.id = index
        }

    override fun getKeyValue(): Pair<String, String> = tag.toString() to binding.radioGroup.checkedRadioButton.text.toString()

    private val RadioGroup.checkedRadioButton
            get() = get(checkedRadioButtonId) as RadioButton

}