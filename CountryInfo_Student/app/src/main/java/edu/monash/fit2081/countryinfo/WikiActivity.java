package edu.monash.fit2081.countryinfo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WikiActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);
        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        String countryName = getIntent().getStringExtra("country name");
        webView.loadUrl("https://en.wikipedia.org/wiki/"+countryName);
    }
}
