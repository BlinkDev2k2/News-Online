package com.blink.newsonline.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.blink.newsonline.R

class DetailNewsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl(intent.getStringExtra("LINK_NEWS")!!.trim())
        webView.webViewClient = WebViewClient()
    }
}