package com.apiumhub.vyou_ui.register.ui.exception

import android.view.View

class ValidationException(val view: View) : Exception("Validation failed for view: $view")