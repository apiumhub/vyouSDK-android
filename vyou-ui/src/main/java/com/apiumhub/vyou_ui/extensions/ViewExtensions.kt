package com.apiumhub.vyou_ui.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View

fun String.applySpans(context: Context,
    url: String,
    vararg pairs: Pair<String, Any>
): SpannableStringBuilder {
    val spannable = SpannableStringBuilder(this)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }
    pairs.forEach {
        val start = this.indexOf(it.first)
        val end = start + it.first.length
        spannable.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spannable
}