package com.apiumhub.vyou_ui.register.ui.components

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.databinding.VyouDateInputBinding
import com.apiumhub.vyou_ui.register.domain.DateField
import java.util.*


internal fun DateInputView(context: Context, inputfield: DateField) = DateInputView(context).apply { render(inputfield) }
internal class DateInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding: VyouDateInputBinding = VyouDateInputBinding.inflate(LayoutInflater.from(context), this, true)

    override fun getKeyValue(): Pair<String, String> = tag.toString() to binding.dateInputEt.text.toString()

    fun render(inputField: DateField) {
        tag = inputField.id
        binding.dateInputEt.hint = inputField.id
        binding.dateInputEt.setOnClickListener { openCalendarComponent() }
    }

    private fun openCalendarComponent() {
        val c: Calendar = Calendar.getInstance()
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val year: Int = c.get(Calendar.YEAR)

        val datePickerDialog =
            DatePickerDialog(context,
                OnDateSetListener { _, year, month, dayOfMonth ->
                    val newMonth = month +1
                    val date = "$dayOfMonth/$newMonth/$year"
                    binding.dateInputEt.setText(date) },
                year,
                month,
                day
            )
        datePickerDialog.show()
    }
}
