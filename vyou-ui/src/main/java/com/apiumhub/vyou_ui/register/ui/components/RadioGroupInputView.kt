package com.apiumhub.vyou_ui.register.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RadioButton
import com.apiumhub.vyou_ui.databinding.VyouRadioGroupInputBinding
import com.apiumhub.vyou_ui.register.domain.RadioGroupField

internal fun RadioGroupInputView(context: Context, inputfield: RadioGroupField) = RadioGroupInputView(context).apply { render(inputfield) }

internal class RadioGroupInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding = VyouRadioGroupInputBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var currentSelected: RadioButton

    fun render(inputField: RadioGroupField) {
        tag = inputField.id
        inputField.options
            .map(::mapToRadioButton)
            .forEach(binding.radioGroup::addView)
    }

    private fun mapToRadioButton(field: String) =
        RadioButton(context).also { radioButton ->
            radioButton.text = field
            setOnClickListener {
                currentSelected = radioButton
            }
        }

    override fun getKeyValue(): Pair<String, String> = tag.toString() to currentSelected.text.toString()
}