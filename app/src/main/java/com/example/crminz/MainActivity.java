package com.example.crminz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.net.Uri;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

        private WebView webView;
        private ProgressBar progressBar; // Ensure this line is present

    String url = "https://crm.inzeworld.ru/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);
        webView = findViewById(R.id.webView);
        webView.loadUrl(url);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);



        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                progressBar.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
            }

        @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                String url = uri.toString();

                if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
                    return false;
                }

                if (url.startsWith("intent")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        Log.e("MainActivity", "Error parsing intent URL", e);
                    }
                }
                return true;
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
