package org.atmarkcafe.otocon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.RulesBinding;

import io.reactivex.annotations.NonNull;

import static android.support.v4.content.ContextCompat.startActivity;

public class TermDialog extends Dialog {
    private String title;
    private String url;

    public TermDialog(@NonNull Context context, String title, String url) {
        super(context, R.style.AppTheme_Dialog);
        setCancelable(false);

        this.title = title;
        this.url = url;

        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    private void init() {
        RulesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_rules, null, false);

        binding.title.setText(title);

        //Setting webview
        WebView webView = binding.webViewRules;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intentTel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(getContext(), intentTel, null);
                } else if (url.startsWith("mailto:")) {
                    url = url.replaceFirst("mailto:", "");
                    url = url.trim();
                    Intent intentMail = new Intent(Intent.ACTION_SEND);
                    intentMail.setType("plain/text").putExtra(Intent.EXTRA_EMAIL, new String[]{url});
                    startActivity(getContext(), intentMail, null);
                } else {
                    view.clearCache(true);
                    view.clearHistory();

                    view.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                binding.loadingLayout.getRoot().setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                binding.loadingLayout.getRoot().setVisibility(View.GONE);
            }
        });

        ImageView imageView = binding.ivClose;
        imageView.setOnClickListener(v -> dismiss());
        setContentView(binding.getRoot());
    }
}
