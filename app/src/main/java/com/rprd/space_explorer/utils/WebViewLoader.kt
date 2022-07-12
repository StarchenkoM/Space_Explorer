package com.rprd.space_explorer.utils

import android.webkit.WebView

interface WebViewLoader {
    fun loadWebView(url: String, container: WebView)
}