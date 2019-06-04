package org.atmarkcafe.otocon.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView

import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.RematchFlowStatusBinding

import android.support.v4.content.ContextCompat.startActivity


class test(context: Context, internal var title: String, internal var url: String) : Dialog(context, R.style.AppTheme_Dialog) {

    init {
        setCancelable(false)

        init()
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    private fun init() {
        val binding = DataBindingUtil.inflate<RematchFlowStatusBinding>(LayoutInflater.from(context), R.layout.dialog_rematch_status, null, false)

        binding.title.text = title

        //Setting webview
        val webView = binding.webViewRules
        webView.settings.javaScriptEnabled = true
        webView.isVerticalScrollBarEnabled = false
        webView.loadUrl(url)

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                var url = url
                if (url.startsWith("tel:")) {
                    val intentTel = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(context, intentTel, null)
                } else if (url.startsWith("mailto:")) {
                    url = url.replaceFirst("mailto:".toRegex(), "")
                    url = url.trim { it <= ' ' }
                    val intentMail = Intent(Intent.ACTION_SEND)
                    intentMail.setType("plain/text").putExtra(Intent.EXTRA_EMAIL, arrayOf(url))
                    startActivity(context, intentMail, null)
                } else {
                    view.clearCache(true)
                    view.clearHistory()

                    view.loadUrl(url)
                }

                return true
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)

                binding.loadingLayout.root.visibility = View.VISIBLE
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)

                binding.loadingLayout.root.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                binding.loadingLayout.root.visibility = View.GONE
            }
        }

    }
}
