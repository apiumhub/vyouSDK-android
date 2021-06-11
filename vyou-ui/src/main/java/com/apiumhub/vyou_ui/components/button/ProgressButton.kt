package com.apiumhub.vyou_ui.components.button

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private val binding = VyouProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)

  init {
    initializeAttributes(attrs)
  }


  private fun buttonIsEnabled(enable: Boolean) {
    binding.button.isEnabled = enable
  }

  fun setOnClickListener(onClick: () -> Unit) {
    binding.button.setOnClickListener {
      onClick()
    }
  }

  private fun loading() {
    binding.button.isEnabled = false
    binding.button.setTextColor(ContextCompat.getColor(context, android.R.color.transparent))
    binding.progress.isVisible = true
  }

  fun restore() {
    buttonIsEnabled(true)
    binding.progress.isVisible = false
    binding.button.setTextColor(ContextCompat.getColor(context, R.color.vyou_on_primary))
  }

  private fun initializeAttributes(attrs: AttributeSet?) {
    attrs?.let {
      val typedArray = context.obtainStyledAttributes(
        it,
        R.styleable.ProgressButton, 0, 0
      )
      val ctaText = resources.getText(
        typedArray.getResourceId(
          R.styleable.ProgressButton_ctaButton,
          R.string.field_unknown
        )
      )

      val iconDrawable = typedArray.getDrawable(R.styleable.ProgressButton_iconButton)
      if (iconDrawable != null) {
        val iconTint = typedArray.getColor(R.styleable.ProgressButton_iconButtonTint, 0)
        val iconPadding = typedArray.getDimensionPixelSize(R.styleable.ProgressButton_iconButtonPadding, 0)
        binding.button.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null)
        if (iconTint != 0) TextViewCompat.setCompoundDrawableTintList(binding.button, ColorStateList.valueOf(iconTint))
        if (iconPadding != 0) binding.button.compoundDrawablePadding = iconPadding
      }
      binding.button.text = ctaText
      typedArray.recycle()
    }
  }
}