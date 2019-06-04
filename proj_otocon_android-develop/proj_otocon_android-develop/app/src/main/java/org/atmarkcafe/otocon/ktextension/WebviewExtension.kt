package org.atmarkcafe.otocon.ktextension

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.*
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog

fun WebView.setDefaultClient(view: android.view.View){
    this.webViewClient = DefaultWebClient(view)
    this.webChromeClient = DefaultChromeWebClient(view)
}

class DefaultWebClient(val  mView:android.view.View) : WebViewClient(){
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        var urlLate : String? = url
        if (urlLate!!.startsWith("tel:")) {
            val intentTel = Intent(Intent.ACTION_DIAL, Uri.parse(urlLate))
            view?.context?.startActivity(intentTel)
        } else if (urlLate.startsWith("mailto:")) {
            urlLate = urlLate.replaceFirst("mailto:".toRegex(), "")
            urlLate = urlLate.trim()
            val intentMail = Intent(Intent.ACTION_SEND)
            intentMail.setType("plain/text").putExtra(Intent.EXTRA_EMAIL, arrayOf(urlLate))
            view?.context?.startActivity(intentMail)
        } else {
            view?.loadUrl(urlLate)
        }

        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        mView.visibility = View.VISIBLE
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)

        mView.visibility = View.GONE
        view?.visibility = View.GONE
        PopupMessageErrorDialog(view!!.context).show()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        mView.visibility = View.GONE
    }
}

class DefaultChromeWebClient(val  mView:android.view.View) :WebChromeClient(){
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress == 100) {
            mView.visibility = View.GONE
        }
    }
}