package com.rprd.space_explorer.utils

import androidx.annotation.StringRes

interface ToastUtil {
    fun showToast(message: String)
    fun showToast(@StringRes message: Int)
}