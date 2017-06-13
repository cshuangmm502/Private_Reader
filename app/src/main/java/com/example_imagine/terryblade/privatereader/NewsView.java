package com.example_imagine.terryblade.privatereader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Terryblade on 2017/5/6.
 */
public class NewsView extends Activity {
    private WebView webView;
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        ActivityCollector.AddActivity(this);
        setContentView(R.layout.activity_web_news);
        webView=(WebView)findViewById(R.id.webview);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        Toast.makeText(NewsView.this,url,Toast.LENGTH_SHORT).show();
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.RemoveActivity(this);
    }
}
