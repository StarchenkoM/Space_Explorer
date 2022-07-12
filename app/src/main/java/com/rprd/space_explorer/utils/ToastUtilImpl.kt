package com.rprd.space_explorer.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

class ToastUtilImpl(val context: Context) : ToastUtil {
    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showToast(@StringRes message: Int) {
        Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG).show()
    }
}