package com.surgenews.surge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ReadAndRecordActivty extends AppCompatActivity {

    WebView mWebview;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_record_activity);
        String url = getIntent().getStringExtra("URL");
        mWebview = (WebView) findViewById(R.id.wv_news);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl(url);

        mWebview.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

        });

    }
}
