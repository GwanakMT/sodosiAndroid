package com.sodosi.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.sodosi.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val docsUrl = when(intent.getStringExtra("KEY_WEBVIEW_TYPE")) {
            TYPE_TERMS_OF_SERVICE -> "https://docs.google.com/document/d/e/2PACX-1vSU4CI7HNfi8IPcL5-27cQCcHf-Hh9IPMftQhz1U-GaEHkfiHJpf7n8kEh2avQAPeBhhbScyf_OyptV/pub"
            TYPE_PRIVACY_POLICY -> "https://docs.google.com/document/d/e/2PACX-1vRv3Wr6RrqK2ohecT9Srg_BRGZhfgwsBkqvAHYe39V69rQprSVK45w-uv3mDaeGn4ngPzigGJMYW3OZ/pub"
            TYPE_OPEN_SOURCE_INFO -> "https://github.com/"
            TYPE_SODOSI_MAKERS -> "https://docs.google.com/document/d/e/2PACX-1vTE2ni7-hvOgWE5A4dedcNHVC2r5oIbddgfWUSH-R3rmdymlH3l9yty92W2Iq03sk3Zq0uf0YgY6sbT/pub"
            else -> "https://github.com/GwanakMT/sodosiAndroid"
        }

        initWebView(getHtmlFrame(docsUrl))
    }

    private fun initWebView(htmlData: String) {
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportMultipleWindows(true)
            }

            webViewClient = SodosiWebViewClient()
            webChromeClient = WebChromeClient()

            loadData(htmlData, "text/html", "UTF-8")
        }
    }

    class SodosiWebViewClient(): WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
    }

    companion object {
        private const val KEY_WEBVIEW_TYPE = "KEY_WEBVIEW_TYPE"
        const val TYPE_TERMS_OF_SERVICE = "TYPE_TERMS_OF_SERVICE"
        const val TYPE_PRIVACY_POLICY = "TYPE_PRIVACY_POLICY"
        const val TYPE_OPEN_SOURCE_INFO = "TYPE_OPEN_SOURCE_INFO"
        const val TYPE_SODOSI_MAKERS = "TYPE_SODOSI_MAKERS"

        fun getIntent(context: Context, type: String): Intent {
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra(KEY_WEBVIEW_TYPE, type)
            }
        }

        private fun getHtmlFrame(url: String): String {
            return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>소도시 이용약관</title>
                <style>
                    html,
                    body {
                        width: 99%;
                        height: 99%;
                    }
                    iframe {
                        width: 99%;
                        height: 99%;
                    }
                </style>
            </head>
            <body>
            <iframe
                    src="$url"
                    frameborder="0"></iframe>
            </body>
            </html>
        """.trimIndent()
        }
    }
}
