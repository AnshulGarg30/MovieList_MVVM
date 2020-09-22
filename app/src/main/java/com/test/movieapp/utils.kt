package com.test.movieapp

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Context.dismissKeyboard(view: View?) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
