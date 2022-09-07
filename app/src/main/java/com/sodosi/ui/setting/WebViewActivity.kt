package com.sodosi.ui.setting

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

        val docsUrl = "https://docs.google.com/document/d/e/2PACX-1vTf__1CQ1qDSX9asZQyNh2MPux21FyPeKI0wyBwmpJiE0wcHp06kN-8HjyqMQErPbhKpZOFdOS55ofE/pub?embedded=true"
        val htmlData = """
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
                    src="$docsUrl"
                    frameborder="0"></iframe>
            </body>
            </html>
        """.trimIndent()

        initWebView(htmlData)
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
}
