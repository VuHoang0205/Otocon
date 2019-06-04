package org.atmarkcafe.otocon.extesion.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentMenuWebviewBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;

public class WebFragment extends OtoconBindingFragment<FragmentMenuWebviewBinding> {
    public static final String HEADER = "header";
    public static final String URL = "url";
    private String urlCurent;

    @Override
    public int layout() {
        return R.layout.fragment_menu_webview;
    }

    @Override
    public void onCreateView(FragmentMenuWebviewBinding binding) {
        binding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        PopupMessageErrorDialog popupMessageErrorDialog = new PopupMessageErrorDialog(getActivity());
        binding.setHeader(getArguments().getString(HEADER));
        if (isNetworkAvailable()) {
            WebView webView = binding.webView;
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("tel:")) {
                        Intent intentTel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                        startActivity(intentTel);
                    } else if (url.startsWith("mailto:")) {
                        url = url.replaceFirst("mailto:", "");
                        url = url.trim();
                        Intent intentMail = new Intent(Intent.ACTION_SEND);
                        intentMail.setType("plain/text").putExtra(Intent.EXTRA_EMAIL, new String[]{url});
                        startActivity(intentMail);
                    } else {
                        view.setScrollY(0);

                        view.loadUrl(url);
                    }

                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    urlCurent = url;
                    super.onPageStarted(view, url, favicon);
                    binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageCommitVisible(WebView view, String url) {
                    super.onPageCommitVisible(view, url);
                    binding.loadingLayout.getRoot().setVisibility(View.GONE);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    if (failingUrl.equals(urlCurent)) {
                        popupMessageErrorDialog.show();
                        view.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress == 100) {
                        binding.loadingLayout.getRoot().setVisibility(View.GONE);
                    }
                }
            });

            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.setVerticalScrollBarEnabled(false);

            webView.loadUrl(getArguments().getString(URL));

        } else {
            popupMessageErrorDialog.show();
            binding.loadingLayout.getRoot().setVisibility(View.GONE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
