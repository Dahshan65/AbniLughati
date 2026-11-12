package com.dahshansoft.abni;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w = getWindow();
        if (Build.VERSION.SDK_INT >= 21) w.setStatusBarColor(Color.parseColor("#3b30c9"));

        web = new WebView(this);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);          // لحفظ تقدّم الطالب (localStorage)
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setTextZoom(100);                    // حتى لا يكبّر حجم خط الهاتف تصميم التطبيق

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                return openOutside(req.getUrl().toString());
            }
            @Override
            @SuppressWarnings("deprecation")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return openOutside(url);
            }
        });

        if (savedInstanceState != null) web.restoreState(savedInstanceState);
        else web.loadUrl("file:///android_asset/index.html");
    }

    // روابط واتساب وكتاب الوزارة وأي رابط خارجي تُفتح خارج التطبيق
    private boolean openOutside(String url) {
        if (url.startsWith("file:///android_asset/")) return false;
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception ignored) { }
        return true;
    }

    // زر الرجوع في الهاتف يرجع داخل التطبيق، ويخرج فقط من الشاشة الرئيسية
    @Override
    public void onBackPressed() {
        web.evaluateJavascript(
            "(function(){var b=document.getElementById('backBtn');" +
            "if(b&&b.style.display!=='none'){goBack();return 'in';}" +
            "var m=document.getElementById('modal');if(m&&m.classList.contains('show')){closeModal();return 'in';}" +
            "var a=document.querySelector('.screen.active');" +
            "if(a&&a.id!=='home'&&typeof tab==='function'&&document.getElementById('app').style.display!=='none'){tab('home');return 'in';}" +
            "return 'out';})()",
            value -> {
                if (value == null || value.contains("out")) MainActivity.super.onBackPressed();
            });
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }
}
