package com.apiumhub.vyou_ui.components.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouFieldOutputBinding

internal fun FieldOutputView(context: Context, id: String) =
    FieldOutputView(context).apply { render(id) }

internal class FieldOutputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VYouOutputComponent {

    private val binding: VyouFieldOutputBinding =
        VyouFieldOutputBinding.inflate(LayoutInflater.from(context), this, true)

    override lateinit var id: String

    override var visible: Boolean
        get() = isVisible
        set(value) {
            isVisible = value
        }

    override fun setValue(value: String) {
        binding.tvValue.text = value
    }

    fun render(id: String) {
        this.id = id
        tag = id
        binding.tvField.text = getTitle(context)
    }

    private fun getTitle(context: Context) =
        when (id) {
            "birth" -> context.getString(R.string.field_birthdate_label)
            "country" -> context.getString(R.string.field_country_label)
            "name" -> context.getString(R.string.field_name_label)
            "phone" -> context.getString(R.string.field_phone_label)
            "gender" -> context.getString(R.string.field_gender_label)
            "surname" -> context.getString(R.string.field_surname_label)
            "vyou_internal_email" -> context.getString(R.string.field_email_label)
            "vyou_internal_password" -> context.getString(R.string.field_password_label)
            else -> id.lowercase().replaceFirstChar { it.uppercase() }
        }
}