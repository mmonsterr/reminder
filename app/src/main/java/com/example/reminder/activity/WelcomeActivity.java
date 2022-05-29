package com.example.reminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.reminder.R;


public class WelcomeActivity extends AppCompatActivity {
    WebView webView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        //创建网页界面
        webView=(WebView)findViewById(R.id.wv1);
        url="http://mmonsterr.top/";
        //设置WebView加载回调
        webView.setWebViewClient(new mywebview());
        //设置WebView是否允许执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("http://mmonsterr.top/");
    }
    private class mywebview extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            //判断是否为url网站，
            if ("http://mmonsterr.top/".equals(Uri.parse(url).getHost())) {
                //加载url
                view.loadUrl(url);

                return true;
            }
            else {
                //启动处理url的活动
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

                return true;
            }
        }
    }
}
