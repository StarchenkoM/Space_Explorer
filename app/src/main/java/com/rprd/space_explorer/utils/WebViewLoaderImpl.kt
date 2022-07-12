package com.rprd.space_explorer.utils

import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewLoaderImpl : WebViewLoader {
    override fun loadWebView(url: String, container: WebView) {
        container.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }
}