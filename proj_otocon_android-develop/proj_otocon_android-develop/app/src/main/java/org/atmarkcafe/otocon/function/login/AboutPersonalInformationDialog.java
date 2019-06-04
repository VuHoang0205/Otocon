package org.atmarkcafe.otocon.function.login;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogAboutPersonalInformationBinding;

public class AboutPersonalInformationDialog extends Dialog {

    public AboutPersonalInformationDialog(@NonNull Context context) {
        super(context, R.style.AppTheme_Dialog);
    }

    public AboutPersonalInformationDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.AppTheme_Dialog);
    }

    private ErrorLoadWebviewListener mlistener;

    interface ErrorLoadWebviewListener {
        void error();
    }

    public void setErrorLoadWebviewListener(ErrorLoadWebviewListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogAboutPersonalInformationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_about_personal_information, null, false);

        WebView webView = binding.webView;

        //Setting webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(BuildConfig.LINK_LOGIN_PRIVACY);

        binding.ivClose.setOnClickListener(v->dismiss());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                binding.loadingLayout.getRoot().setVisibility(View.GONE);

                if (mlistener != null) {
                    mlistener.error();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                binding.loadingLayout.getRoot().setVisibility(View.GONE);
            }
        });

        setContentView(binding.getRoot());
    }
}
